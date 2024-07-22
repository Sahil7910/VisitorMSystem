package com.distna.web.login;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class emailsender
{
	private static String USER_NAME = "timeattendancedis";  // GMail user name (just the part before "@gmail.com")
	private static String PASSWORD = "Digital123"; // GMail password

	private static String RECIPIENT = "timeattendancedis@gmail.com";

	String flag="fail";
	
	

	public void sendFromGMail(String bodydata) {
		String from = USER_NAME;
	    String pass = PASSWORD;
	    String[] to = { RECIPIENT }; // list of recipient email addresses
	    String subject = "Request for the liecense key for Attendance Device Software";
	    String body = bodydata;
		
		Properties props = System.getProperties();
	    String host = "smtp.gmail.com";

	    props.put("mail.smtp.starttls.enable", "true");

	    props.put("mail.smtp.ssl.trust", host);
	    props.put("mail.smtp.user", from);
	    props.put("mail.smtp.password", pass);
	    props.put("mail.smtp.port", "587");
	    props.put("mail.smtp.auth", "true");


	    Session session = Session.getDefaultInstance(props);
	    MimeMessage message = new MimeMessage(session);

	    try {


	        message.setFrom(new InternetAddress(from));
	        InternetAddress[] toAddress = new InternetAddress[to.length];

	        // To get the array of addresses
	        for( int i = 0; i < to.length; i++ ) {
	            toAddress[i] = new InternetAddress(to[i]);
	        }

	        for( int i = 0; i < toAddress.length; i++) {
	            message.addRecipient(Message.RecipientType.TO, toAddress[i]);
	        }


	        message.setFrom(new InternetAddress(from, " "));
	        message.setSubject(subject);
	        message.setText(body);


	        Transport transport = session.getTransport("smtp");


	        transport.connect(host, from, pass);
	        transport.sendMessage(message, message.getAllRecipients());
	        System.out.println("Email send successfully");
	        flag="success";
	        transport.close();

	       
	    }
	    catch (AddressException ae) {
	        //ae.printStackTrace();
	    }
	    catch (MessagingException me) {
	       // me.printStackTrace();
	    } catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	  
	    }
	
	
}
