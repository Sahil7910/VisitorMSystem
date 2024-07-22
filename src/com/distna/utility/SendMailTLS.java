package com.distna.utility;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMailTLS
{
		public static void main(String[] args) {
		/*final String username = "archanatrimukhe@yahoo.com";
		final String password = "sulochana1411";
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.mail.yahoo.com");
		props.put("mail.smtp.port", "587");
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
 
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("archanatrimukhe@yahoo.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse("anup_kale2910@yahoo.co.in"));
			message.setSubject("Testing Subject");
			message.setText("Dear Anup Kale,"
				+ "\n\n ghari jaaa, please!");
 
			Transport.send(message);
 
			System.out.println("Done");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}*/
			SendMailTLS sendMailTLS=new SendMailTLS();
			sendMailTLS.sendMail("archanatrimukhe@yahoo.com","sulochana1411","smtp.mail.yahoo.com", "587","anup_kale2910@yahoo.co.in","Anup Kale","aaaaaa");
			
	}
		
		public boolean sendMail(String usernameSender,String passwordSender,String smtpHost, String smtpPort,String recieversEmail,String recieversName,String recieversPassword)
		{
			final String username = usernameSender;
			final String password = passwordSender;
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			/*props.put("mail.smtp.host", "smtp.mail.yahoo.com");*/
			props.put("mail.smtp.host",smtpHost);
			/*props.put("mail.smtp.port", "587");*/
			props.put("mail.smtp.port", smtpPort);
			Session session = Session.getInstance(props,
			  new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			  });
	 
			try {
	 
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(usernameSender));
				message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(recieversEmail));
				message.setSubject("Testing Subject");
				message.setText("Dear "+recieversName+","+ "\n\n Your login credentials are \n Username:"+recieversEmail+" \n Password:"+recieversPassword);
				Transport.send(message);
				System.out.println("done");
				return true;
	 
			} catch (MessagingException e) {
				return false;
				/*throw new RuntimeException(e);*/
			}
		}
		
		public boolean sendMail(String usernameSender,String passwordSender,String smtpHost, String smtpPort,String recieversEmail,String recieversName, String leaveSubject, String leaveMessage)  {
			
			final String username = usernameSender;
			final String password = passwordSender;
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			/*props.put("mail.smtp.host", "smtp.mail.yahoo.com");*/
			props.put("mail.smtp.host",smtpHost);
			/*props.put("mail.smtp.port", "587");*/
			props.put("mail.smtp.port", smtpPort);
			Session session = Session.getInstance(props,
			  new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			  });
	 
			try {
	 
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(usernameSender));
				message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(recieversEmail));
				message.setSubject(leaveSubject);
				message.setText(leaveMessage);
				Transport.send(message);
				System.out.println("done");
				return true;
	 
			} catch (MessagingException e) {
				return false;
				/*throw new RuntimeException(e);*/
			}
		}
		

}
