package com.api.user.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.user.config.ApplicationConfig;
import com.api.user.models.Login;
import com.api.user.models.User;
import com.api.user.repository.UserRepository;
import com.api.user.util.EmailUtil;
import com.api.user.util.UserUtil;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ApplicationConfig config;
	
	@Override
	public boolean save(User user) {
		try{
			System.out.println(""+user);
			String encodePassword= config.passwordEncoder()
										 .encode(user.getPassword());
			
			
			user.setPassword(encodePassword);
			System.out.println(user.getPassword());
			System.out.println(encodePassword);
			
			userRepo.save(user);
			
			return true;
		
		}
		catch (Exception e) {
			
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public boolean isDuplicateEmail(String email)
	{
		 boolean flag= userRepo.findAll()
				 				   .stream()
				 				   .filter(user-> user.getEmailid().equals(email)).findFirst().isPresent();
		 
		 return flag;
	}
	
	@Override
	public boolean authenticate(Login login) {
		
		try {

			User user=userRepo.findById(login.getUsername()).orElse(null);
			
			boolean flag= config.passwordEncoder().matches(login.getPassword(), user.getPassword());
			
			if(user.getUsername().equals(login.getUsername()) && flag)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<User> getUsers() {

		try {
			
			List<User> users = userRepo.findAll();
			return users;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public User getUser(String Id )
	{
		return userRepo.findById(Id).orElse(null);
	}
	
	
	
	
	@Override
	public Optional<User> updatePassword(String username, String password) {

		try {
			
			User user = userRepo.findById(username).orElse(null);
			
			if(!user.equals(null))
			{
				user.setPassword(config.passwordEncoder().encode(password));
				userRepo.saveAndFlush(user);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	@Override
	public String getUrl(String service, String username)
	{
		String url= "http://localhost:8080/api/userservice/"+service+"/"+UserUtil.generateToken(username);
		/* System.out.println(url); */
		return url;
	}
	
	
	@Override
	public String verifyToken(String token) {

		String id=UserUtil.verifyToken(token);
		User user=userRepo.findById(id).orElse(null);
		
		if(!user.equals(null))
		{
			return user.getUsername();
		}
		return null;
	}
	
}
