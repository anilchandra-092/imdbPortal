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
	public boolean sendMailTo() {
		
		boolean result = false;
		String host = "localhost";
		final String adminMailId = "lakshmidurga.chalamalasetti@alacriti.com";
		final String password = "l@kkikrish";

		String toAddress = "babureddy979@gmail.com";

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
			message.setSubject("javatpoint");
			message.setText("This is simple program of sending email using JavaMail API");

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
