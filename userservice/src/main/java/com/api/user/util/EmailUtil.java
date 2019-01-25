package com.api.user.util;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtil {

	public static void sendEmail(String toEmail, String subject, String body){
		
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
		
		
		try {
			
			MimeMessage message=new MimeMessage(session);
			
			message.setFrom(new InternetAddress("no_reply@gmail.com", "NoReply-JD"));
			
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
			
			message.setSubject(subject);
			
			message.setContent(body, "text/html");
			
			System.out.println("Message is ready.....!!!");
			
			Transport.send(message);
			
			System.out.println("Email is sent...!!!");
			
		}catch (Exception e) {
		
			e.printStackTrace();
		}
	}
	
}
