package com.api.user.util;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.api.user.exception.UserException;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EmailUtil {

	public void sendEmail(String toEmail, String subject, String body) throws UserException{
		
		String fromEmail= "fundoonotes3@gmail.com";
		String password= "pass2pass";
		
		Properties props = new Properties(); 
		props.put("mail.smtp.auth", "true"); 
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
			
		Authenticator auth= new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication(){
				
				return new PasswordAuthentication(fromEmail, password);
			}
		};
		
		Session session= Session.getInstance(props, auth); 
		
		send(session, toEmail, subject, body);
	}
	
	private void send(Session session,String toEmail, String subject, String body)
	{
		try {
		
			System.out.println(session.toString());
			
			MimeMessage message=new MimeMessage(session);
			
			message.setFrom(new InternetAddress("fundoonotes3@gmail.com", "NoReply-JD0"));
			
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
			
			message.setSubject(subject);
			
			message.setContent(body, "text/html");
			
			Transport.send(message);
		}
		catch (Exception e) {

			throw new UserException(404, e.getMessage());
		}
			
	
	}
	
}
