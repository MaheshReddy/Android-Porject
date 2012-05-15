package com.es.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import android.app.Activity;
import android.os.Bundle;

public class Email extends Activity {
	private static ArrayList<String> msg;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		if(extras != null)
		 msg = extras.getStringArrayList("args");
		else{
			msg = new ArrayList<String>();
			msg.add("Default:");
		}
		final String username = "travelwatchmaster@gmail.com";
		final String password = "interOP@123";
 
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
 
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("travelwatchmaster@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse("travelwatchmaster@gmail.com"));
			Iterator<String> itr = msg.iterator();
			while(itr.hasNext()){
			String msg1 = itr.next();
			message.setSubject(msg1);
			message.setText(msg1);
			Transport.send(message);
			System.out.println("Done");
		}
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		//Intent intent = new Intent();
		setResult(RESULT_OK);
		System.out.println("Email.onCreate() " + "return");
		finish();
	}
}
