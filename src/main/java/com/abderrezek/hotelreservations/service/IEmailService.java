package com.abderrezek.hotelreservations.service;

public interface IEmailService {
	
	void sendSimpleMessage(String to, String from, String subject, String text);

}
