package com.api.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.user.models.User;
import com.api.user.util.EmailUtil;

@Service
public class EmailServiceImpl implements EmailService{

	@Autowired
	private UserService userService;
	
	@Override
	public void sendMail(String userName, String subject, String body) {
	
		User user=userService.getUser(userName);
		if(user!=null)
		{
			EmailUtil.sendEmail(user.getEmailid(), subject, body);
		}
	}
	
}
