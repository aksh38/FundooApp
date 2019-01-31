package com.api.user.util;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.api.user.exception.UserException;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EmailUtil {

	@Autowired
	private JavaMailSender sender;

	/*
	 * public void emailConfig(String toEmail, String subject, String body) throws
	 * UserException{
	 * 
	 * String fromEmail= "fundoonotes3@gmail.com"; String password= "pass2pass";
	 * 
	 * Authenticator auth= new Authenticator() {
	 * 
	 * @Override protected PasswordAuthentication getPasswordAuthentication(){
	 * 
	 * return new PasswordAuthentication(fromEmail, password); } };
	 * 
	 * Session session= Session.getInstance(props, auth);
	 * 
	 * }
	 */
	public void sendEmail(String toEmail, String subject, String body) throws UserException{
		try {
		
			MimeMessage message=sender.createMimeMessage();
			
			MimeMessageHelper helper=new MimeMessageHelper(message);
			
			helper.setFrom(new InternetAddress("fundoonotes3@gmail.com", "NoReply-JD0"));
			
			helper.setTo(InternetAddress.parse(toEmail));
			
			helper.setSubject(subject);
			
			helper.setText(body);
			
			sender.send(message);
		}
		catch (Exception e) {

			e.printStackTrace();
			throw new UserException(404, "Mail sending failed.....");
		}
			
	
	}
	
}
