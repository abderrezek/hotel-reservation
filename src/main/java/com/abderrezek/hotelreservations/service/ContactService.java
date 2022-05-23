package com.abderrezek.hotelreservations.service;

import org.springframework.stereotype.Service;

import com.abderrezek.hotelreservations.entity.Contact;
import com.abderrezek.hotelreservations.form.ContactForm;
import com.abderrezek.hotelreservations.repository.ContactRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ContactService implements IContactService {
	
	private final ContactRepository contactRepository;

	@Override
	public Contact create(ContactForm contactForm) {
		Contact contact = contactForm.toEntity();
		return contactRepository.save(contact);
	}

}
