package com.abderrezek.hotelreservations.service;

import java.text.SimpleDateFormat;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import com.abderrezek.hotelreservations.config.EmailConfiguration;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class EmailService implements IEmailService {

	private final EmailConfiguration emailConfiguration;
	
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
		mailSenderImpl.setHost(emailConfiguration.getHost());
		mailSenderImpl.setPort(emailConfiguration.getPort());
		mailSenderImpl.setUsername(emailConfiguration.getUsername());
		mailSenderImpl.setPassword(emailConfiguration.getPassword());
		return mailSenderImpl;
	}
	
	public void sendSimpleMessage(String to, String from, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setFrom(from);
		message.setSubject(subject);
		message.setText(text);
		
		getJavaMailSender().send(message);
	}
	
}
