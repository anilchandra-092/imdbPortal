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
	
	public  boolean send(String to,String status){  
		
		boolean result=false;
		final String from = "anilkumarreddy.gangammagari@alacriti.com";
		final String password = "8333032071";
		String subject="Imdb portal Registration";
		String msg;
		
        //Get properties object    
        Properties props = new Properties();    
        props.put("mail.smtp.host", "smtp.gmail.com");    
        props.put("mail.smtp.socketFactory.port", "465");    
        props.put("mail.smtp.socketFactory.class",    
                  "javax.net.ssl.SSLSocketFactory");    
        props.put("mail.smtp.auth", "true");    
        props.put("mail.smtp.port", "465");    
        //get Session   
        Session session = Session.getInstance(props,    
         new javax.mail.Authenticator() {    
         protected PasswordAuthentication getPasswordAuthentication() {    
         return new PasswordAuthentication(from,password);  
         }    
        });
        
        
        //compose message    
        try {    
         MimeMessage message = new MimeMessage(session);    
         message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));    
         
         if("approve".equalsIgnoreCase(status)){
				msg="Your Registration Approved. Now u can login in ImdbPortal";
			}
			else{
				msg="Your Registration Rejected by Admin";
			}
         
         message.setSubject(subject);    
         message.setText(msg);    
         
         //send message  
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
