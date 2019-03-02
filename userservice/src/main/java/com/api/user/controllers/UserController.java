package com.api.user.controllers;

import java.math.BigInteger;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.user.dto.CollabUserInfo;
import com.api.user.dto.LoginDto;
import com.api.user.dto.UserDto;
import com.api.user.exception.UserException;
import com.api.user.models.User;
import com.api.user.response.Response;
import com.api.user.service.UserService;

/**
 * @author admin1
 * 
 *         RestController- allows to use PUT, PATCH, DELETE, GET, POST mapping
 *         methods
 * 
 *         RequestMapping - maps the request to appropriate url
 * 
 *         RequestBody- maps the HttpRequest body to a object/domain
 * 
 */
@RestController
@CrossOrigin(origins="http://localhost:4200",exposedHeaders= {"jwt_token"})
@RequestMapping("/api/user/")
public class UserController {

	@Autowired
	private UserService userService;


	/**
	 * @param userDto- user data transfer object for requesting the user details
	 * 
	 * @return response object with status code and status message
	 * 
	 * @throws UserException
	 */
	@PostMapping
	public ResponseEntity<?> register(@Valid @RequestBody UserDto userDto, BindingResult result) throws UserException {
		
		
		
		if(result.hasErrors()) {
			
			throw new UserException(406,"Invalid Arguements");
		
		}
		
		userService.save(userDto);
		
		Response response=new Response();
		
		response.setStatusCode(200);
		response.setStatusMessage("Registered Successfully.....");

		return new ResponseEntity<Response>(response, HttpStatus.OK);

	}

	
	/**
	 * @param login- username and password model for authentication
	 * 
	 * @return response object with status code and status message
	 * 
	 * @throws UserException
	 * 
	 */
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto, BindingResult result, HttpServletResponse httpResponse) throws UserException {

		if(result.hasErrors()) {
			
			throw new UserException(406, " Invalid Login Details.....");
			
		}
		
		String token=userService.login(loginDto);
		
		httpResponse.addHeader("jwt_token", token);

		Response response= new Response();
		
		response.setStatusCode(200);
		response.setStatusMessage("Login Successfull....");

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	/**
	 * @return list of users
	 * 
	 * @throws UserException
	 */
	@GetMapping("/users")
	public ResponseEntity<?> getUsers() throws UserException {
		
		return new ResponseEntity<List<User>>(userService.getUsers(), HttpStatus.OK);
		
	}
	
	/**
	 * @param token- required a JWT token for verification as a path variable
	 * 
	 * @return response object with status code and status message
	 * 
	 * @throws UserException
	 */
	@GetMapping("/verification/{token}")
	public ResponseEntity<?> verify(@PathVariable String token) throws UserException {
		
		userService.verifyToken(token);

		Response response= new Response();
		
		response.setStatusCode(200);
		response.setStatusMessage(" Verified Successfully....");

		return new ResponseEntity<Response>(response, HttpStatus.OK);			
	}


	
	/**
	 * @param token- required a JWT token for verification as a path variable
	 * 
	 * @return response object with status code and status message
	 * 
	 * @throws UserException
	 */
	@GetMapping("/forgetPassword")
	public ResponseEntity<?> forgetPassword(@RequestParam String userName) throws UserException {

		userService.forgetPassword(userName);
		
		Response response= new Response();
		
		response.setStatusCode(202);
		response.setStatusMessage("Email send successfully, Please verify......");

		return new ResponseEntity<Response>(response, HttpStatus.OK);

	}

	/**
	 * @param token- required a JWT token for verification as a path variable 
	 * 
	 * @return response object with status code and status message
	 * 
	 * @throws UserException
	 */

	@PutMapping("/resetPassword/{token}")
	public ResponseEntity<?> resetPassword(@PathVariable String token, @RequestParam String password)
			throws UserException {

		userService.resetPassword(token, password);
		
		Response response= new Response();
		
		response.setStatusCode(200);
		response.setStatusMessage("Password Updated.....");

		return new ResponseEntity<Response>(response, HttpStatus.OK);

	}
	

	@DeleteMapping
	public ResponseEntity<?> deleteUser(@RequestParam Long userId)
	{
		userService.deleteUser(userId);
		
		Response response= new Response();
		
		response.setStatusCode(200);
		response.setStatusMessage("User deleted.....");

		return new ResponseEntity<Response>(response, HttpStatus.OK);
		
	}
	
	@GetMapping("/{userName}")
	public User getUserByUserName(@PathVariable String userName)
	{
		User user=userService.getUserByUserName(userName);
		
		return user;
	}
	
	@GetMapping("/email/{emailId}")
	public Long getUserByEmailId(@PathVariable String emailId)
	{
		return userService.getUserByEmailId(emailId);
	}
	
	@PostMapping("/details")
	public List<CollabUserInfo> getUserDetails(@RequestBody List<Long> userIds)
	{
		return userService.getUserDetails(userIds);
	}

}
