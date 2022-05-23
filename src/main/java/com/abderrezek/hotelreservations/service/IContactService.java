package com.abderrezek.hotelreservations.service;

import org.springframework.stereotype.Service;

import com.abderrezek.hotelreservations.entity.Contact;
import com.abderrezek.hotelreservations.form.ContactForm;

@Service
public interface IContactService {
	
	Contact create(ContactForm contactForm);

}
