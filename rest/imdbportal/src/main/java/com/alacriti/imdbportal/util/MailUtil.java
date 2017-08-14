package com.alacriti.imdbportal.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtil {
	public MailUtil() {}
	public boolean sendMailTo(String mailId,String status) {
		
		boolean result = false;
		String host = "localhost";
		final String adminMailId = "anilkumarreddy.gangammagari@alacriti.com";
		final String password = "8333032071";

		String toAddress = mailId;

		// Get the session object
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(adminMailId, password);
					}
				});

		// Compose the message
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(adminMailId));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
			message.setSubject("Imdb portal Registration");
			if("approve".equalsIgnoreCase(status)){
				message.setText("Your Registration Approved. Now u can login in ImdbPortal");
			}
			else{
				message.setText("Your Registration Rejected by Admin");
			}
			

			// send the message
			Transport.send(message);

			System.out.println("message sent successfully...");
			
			result=true;
		} catch (MessagingException e) {
			e.printStackTrace();
			System.out.println("Fail in sending Mail");
			result=false;
		}
		return result;
	}
}
