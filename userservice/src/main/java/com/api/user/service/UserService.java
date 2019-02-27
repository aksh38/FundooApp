package com.api.user.service;

import java.math.BigInteger;
import java.util.List;

import com.api.user.dto.CollabUserInfo;
import com.api.user.dto.LoginDto;
import com.api.user.dto.UserDto;
import com.api.user.exception.UserException;
import com.api.user.models.User;

/**
 * @author admin1
 *
 * @interface User Service is an interface which provides registration, login,
 *            validation and forget password services for user
 *
 *
 */
public interface UserService {

	/**
	 * Saves the user details in the database, if duplicate entry is found
	 * UserException is thrown
	 * 
	 * @param userDto
	 * @return User with their details...
	 * @throws UserException
	 */
	User save(UserDto userDto) throws UserException;

	/**
	 * @param loginDto
	 * @return
	 * @throws UserException
	 */
	String login(LoginDto loginDto) throws UserException;

	/**
	 * @return
	 * @throws UserException
	 */
	List<User> getUsers() throws UserException;

	/**
	 * @param username
	 * @param password
	 * @throws UserException
	 */
	void resetPassword(String username, String password) throws UserException;

	/**
	 * @param token
	 * @return
	 * @throws UserException
	 */
	String verifyToken(String token) throws UserException;

	/**
	 * @param userId
	 * @return
	 */
	User getUser(Long userId);

	/**
	 * @param userId
	 */
	void deleteUser(Long userId);

	/**
	 * @param username
	 * @throws UserException
	 */
	void forgetPassword(String username) throws UserException;

	/**
	 * @param userName
	 * @return
	 */
	User getUserByUserName(String userName);

	/**
	 * @param emailId
	 * @return
	 */
	Long getUserByEmailId(String emailId);

	/**
	 * @param userIds
	 * @return
	 */
	List<CollabUserInfo> getUserDetails(List<BigInteger> userIds);


}