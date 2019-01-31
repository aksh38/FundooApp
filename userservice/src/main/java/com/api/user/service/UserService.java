package com.api.user.service;

import java.util.List;

import com.api.user.dto.LoginDto;
import com.api.user.dto.UserDto;
import com.api.user.exception.UserException;
import com.api.user.models.User;


/**
 * @author admin1
 *
 *@interface User Service is an interface which provides 
 *registration, login, validation and forget password services for user
 *
 *
 */
public interface UserService {

	User save(UserDto userDto) throws UserException;
	
	String login(LoginDto loginDto) throws UserException;
	
	List<User> getUsers() throws UserException;

	void resetPassword(String username, String password) throws UserException;

	String verifyToken(String token) throws UserException;

	User getUser(String Id);

	void forgetPassword(String username) throws UserException;
}
