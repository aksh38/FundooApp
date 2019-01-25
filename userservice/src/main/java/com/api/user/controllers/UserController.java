package com.api.user.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.api.user.models.Login;
import com.api.user.models.User;
import com.api.user.service.EmailService;
import com.api.user.service.UserService;
import com.api.user.util.UserUtil;

@RestController
@RequestMapping("/api/userservice")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private EmailService emailService;
	
	@PostMapping("/register")
	public ResponseEntity<?> save(@Valid @RequestBody User user, HttpServletRequest request) {
		
		
		String url=userService.getUrl("verification", user.getUsername());
		if(!userService.isDuplicateEmail(user.getEmailid()))
		{
			if(	userService.save(user))
			{
				emailService.sendMail(user.getUsername(), "Email Verification ", "To verify email click on link : "+url);
				return new ResponseEntity<String>("Registration successfull", HttpStatus.OK);
			}
			
			return new ResponseEntity<String>("Registration failed .....!!!", HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<String>("Duplicate Email Found .....!!!", HttpStatus.OK);
	
		}
	}

	@GetMapping("/login")
	public ResponseEntity<String> authenticate(@Valid @RequestBody Login login, HttpServletRequest request) {
		
		if(userService.authenticate(login))
		{
			return new ResponseEntity<String>("Login SuccessFull...", HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<String>("Login Unsuccessfull.......", HttpStatus.OK);
		}
	}
	
	
	@GetMapping("/verification/{token}")
	public ResponseEntity<String> verify(@PathVariable String token)
	{
		String username=userService.verifyToken(token);
		if(!username.isEmpty())
		{
			return new ResponseEntity<String>("Verification is Successfull....", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Verification failed....", HttpStatus.OK);
		
	}
	
	@GetMapping("/users")
	public ResponseEntity<?> getUsers()
	{
		List<User> users=userService.getUsers();
		
		if(users.isEmpty())
		{
			return new ResponseEntity<String>("\n No users found in the database", HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<List<User>>(users, HttpStatus.OK);
		}
	}
	
	
	
	 @GetMapping("/forgotpassword") 
	 public ResponseEntity<?> forgotPassword(@RequestParam String username)
	 {
		 
		 User user=userService.getUser(username);
		 
		 if(!user.getUsername().equals(null))
		 {
			 System.out.println("i am here");
			 String url= userService.getUrl("resetpassword", username);
			 emailService.sendMail(username, "Reset Password", "Reset Password : "+url);
			 return new ResponseEntity<String>("Verify the mail.....", HttpStatus.OK);
		 }
		 
		 return new ResponseEntity<String>("Username is not found.....", HttpStatus.OK);
	 }
	 
	 @GetMapping("/resetpassword/{token}")
	 public ResponseEntity<?> resetPassword(@PathVariable String token, HttpServletResponse response) throws IOException
	 {
		 
		 String username = userService.verifyToken(token);
		 
		 if(!username.equals(null))
		 {	
			 response.sendRedirect(userService.getUrl("updatepassword", username));
			 return new ResponseEntity<String>("Password reset successfull", HttpStatus.OK);
		 }
		 else
		 {
			 return new ResponseEntity<String>("Reset password failed...Entry not found...", HttpStatus.OK);
		 }

	 }
	 
	 @RequestMapping("/updatepassword/{token}")
	 public ResponseEntity<?> updatepassword(@PathVariable String token, @RequestParam String password)
	 {
		 String username = userService.verifyToken(token);
		 if(!username.isEmpty())
		 {
			 userService.updatePassword(username, password);
			 return new ResponseEntity<String>("Password reset successfull", HttpStatus.OK);
		 }
		 else
		 {
			 return new ResponseEntity<String>("Reset password failed...Entry not found...", HttpStatus.OK);
		 }
	 }

}
