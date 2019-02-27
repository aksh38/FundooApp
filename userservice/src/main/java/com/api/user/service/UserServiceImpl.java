package com.api.user.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.api.user.dto.CollabUserInfo;
import com.api.user.dto.LoginDto;
import com.api.user.dto.UserDto;

import com.api.user.exception.UserException;

import com.api.user.models.User;

import com.api.user.repository.UserRepository;

import com.api.user.util.EmailUtil;
import com.api.user.util.TokenUtil;
import com.api.user.util.UserUtil;

/**
 * @author admin1
 *
 * @class UserServiceImpl is an implementation of UserService class
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/* (non-Javadoc)
	 * @see com.api.user.service.UserService#save(com.api.user.dto.UserDto)
	 */
	@Override
	public User save(UserDto userDto) throws UserException {

		if (userRepo.findByUserName(userDto.getUserName()).isPresent() || userRepo.findByEmailId(userDto.getEmailId()).isPresent())
			throw new UserException(400, "Duplicate User Details Found");
		String encodePassword = passwordEncoder.encode(userDto.getPassword());
		User user = modelMapper.map(userDto, User.class);
		user.setPassword(encodePassword);
		user=userRepo.save(user);
		
		String url = UserUtil.getUrl("loginVerify", user.getId());
		EmailUtil.sendEmail(userDto.getEmailId(), "Email verification", "Click on the link to verify mail : " + url);
		
		return user;
	}

	@Override
	public String login(LoginDto loginDto) throws UserException {

		return userRepo.findByUserName(loginDto.getUserName())
					   .map(dbUser -> {
						   return authenticate(dbUser, loginDto.getPassword());
					   }).orElseThrow(() -> new UserException(404, "User not found....."));

	}

	/**
	 * @param dbUser
	 * @param password
	 * @return
	 */
	private String authenticate(User dbUser, String password) {
		if (dbUser.isVerified()) {
			if (passwordEncoder.matches(password, dbUser.getPassword()))
				return TokenUtil.generateToken(dbUser.getId());	

			throw new UserException(403, "Not a valid user....");
		}
		throw new UserException(403, "Verification Required....");
	}

	@Override
	public List<User> getUsers() throws UserException {
		List<User> users = userRepo.findAll();
		return users;

	}

	@Override
	public User getUser(Long userId) {
		User user = userRepo.findById(userId).get();
		return user;
	}

	@Override
	public void forgetPassword(String username) throws UserException {

		User user = userRepo.findByUserName(username)
				.orElseThrow(() -> new UserException(404, "Username " + username + " not found....."));

		String url = UserUtil.getUrl("forgetVerify", user.getId());

		EmailUtil.sendEmail(user.getEmailId(), "Reset Password", "Reset Password : " + url);
	}

	@Override
	public void resetPassword(String token, String password) throws UserException {

		Long userId = TokenUtil.verifyToken(token);

		User user = userRepo.findById(userId)
				.orElseThrow(() -> new UserException(400, "Token is not valid........."));

		user.setPassword(passwordEncoder.encode(password));
		
		userRepo.save(user);

	}

	@Override
	public String verifyToken(String token) throws UserException {

		Long userId = TokenUtil.verifyToken(token);
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new UserException(400, "Token is not valid........."));

		user.setVerified(true);

		userRepo.save(user);

		return user.getUserName();
	}

	@Override
	public void deleteUser(Long userId) {
		try {
			userRepo.deleteById(userId);
		}catch (IllegalArgumentException exception) {
			
			throw new UserException(404, exception.getMessage());
		}
		
	}

	@Override
	public User getUserByUserName(String userName) {

		User user= userRepo.findByUserName(userName)
						   .orElseThrow(()->new UserException(404, "User Details Not found"));
		return user;
	}
	
	@Override
	public Long getUserByEmailId(String emailId) {

		User user= userRepo.findByEmailId(emailId)
						   .orElseThrow(()->new UserException(404, "User Details Not found"));
		return user.getId();
	}
	
	@Override
	public List<CollabUserInfo> getUserDetails(List<BigInteger> userIds)
	{
		List<User> users= userRepo.findByIdIn(userIds).orElseThrow(()->new UserException("no such users found"));
		System.out.println(users);
		return users.parallelStream().map(this::setInfoToDto).collect(Collectors.toList());
	}
	
	private CollabUserInfo setInfoToDto(User user)
	{
		return new CollabUserInfo(user.getUserName(), user.getEmailId());
	}
}
