package com.api.user.service;

import java.util.List;
import java.util.Optional;

import com.api.user.models.Login;
import com.api.user.models.User;


public interface UserService {

	boolean save(User user);
	
	boolean isDuplicateEmail(String email);
	
	boolean authenticate(Login login);
	
	List<User> getUsers();

	Optional<User> updatePassword(String username, String password);

	
	String getUrl(String service,  String username);
	/* boolean verifyEmailId(String emailId); */

	String verifyToken(String token);

	User getUser(String Id);
}
