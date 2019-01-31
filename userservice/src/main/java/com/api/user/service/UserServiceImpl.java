package com.api.user.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

	@Override
	public User save(UserDto userDto) throws UserException {

		if (userRepo.findByUserName(userDto.getUserName()).isPresent())
			throw new UserException(400, "Duplicate User Details Found");

		String encodePassword = passwordEncoder.encode(userDto.getPassword());

		User user = modelMapper.map(userDto, User.class);

		user.setPassword(encodePassword);
		
		String url = UserUtil.getUrl("verification", userDto.getUserName());

		EmailUtil.sendEmail(userDto.getEmailId(), "Email verification", "Click on the link to verify mail : " + url);

		return userRepo.save(user);

	}

	@Override
	public String login(LoginDto loginDto) throws UserException {

		return userRepo.findByUserName(loginDto.getUsername())
					   .map(dbUser -> {
						   return authenticate(dbUser, loginDto.getPassword());
					   }).orElseThrow(() -> new UserException(404, "User not found....."));

	}

	private String authenticate(User dbUser, String password) {
		if (dbUser.isVerified()) {
			if (passwordEncoder.matches(password, dbUser.getPassword()))
				return TokenUtil.generateToken(dbUser.getUserName());

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
	public User getUser(String username) {
		User user = userRepo.findByUserName(username).get();
		return user;
	}

	@Override
	public void forgetPassword(String username) throws UserException {

		User user = userRepo.findByUserName(username)
				.orElseThrow(() -> new UserException(404, "Username " + username + " not found....."));

		String url = UserUtil.getUrl("resetpassword", username);

		EmailUtil.sendEmail(user.getEmailId(), "Reset Password", "Reset Password : " + url);
	}

	@Override
	public void resetPassword(String token, String password) throws UserException {

		String username = TokenUtil.verifyToken(token);

		User user = userRepo.findByUserName(username)
				.orElseThrow(() -> new UserException(400, "Token is not valid........."));

		user.setPassword(passwordEncoder.encode(password));

		userRepo.save(user);

	}

	@Override
	public String verifyToken(String token) throws UserException {

		String username = TokenUtil.verifyToken(token);

		User user = userRepo.findByUserName(username)
				.orElseThrow(() -> new UserException(400, "Token is not valid........."));

		user.setVerified(true);

		userRepo.save(user);

		return user.getUserName();
	}

}
