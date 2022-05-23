package com.abderrezek.hotelreservations.controller;

import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.abderrezek.hotelreservations.entity.Contact;
import com.abderrezek.hotelreservations.form.ContactForm;
import com.abderrezek.hotelreservations.service.IContactService;
import com.abderrezek.hotelreservations.service.IEmailService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
@RequestMapping(path = "/contact")
public class ContactController {
	
	private final IContactService contactService;
	private final IEmailService emailService;

	@GetMapping
	public String index(Model model) {
//		model.addAttribute("activeNav", "contact");
		model.addAttribute("contact", new ContactForm(null, "", "", "", ""));
		return "contact";
	}
	
	@PostMapping
	public Object store(
		@Validated @ModelAttribute("contact") ContactForm contactForm,
		BindingResult bindingResult,
		Model model,
		HttpServletRequest request
	) {
		if (bindingResult.hasErrors()) {
			return "contact";
		}
		// save & send message
		Contact contact = contactService.create(contactForm);
		this.sendMessage(contact);
		model.addAttribute("contact", new ContactForm(null, "", "", "", ""));
		model.addAttribute("msgResult", "Merci pour votre envoi !");
		return "contact";
	}
	
	@PostMapping(path = "/ajax")
	@ResponseBody
	public ResponseEntity<String> send(
		@Validated @ModelAttribute("contact") ContactForm contactForm,
		BindingResult bindingResult
	) {
		if (bindingResult.hasErrors()) {
			String errsMsg = bindingResult.getAllErrors()
					.stream().map(x -> x.getDefaultMessage())
					.collect(Collectors.joining("|"));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errsMsg);
		}
		// save & send message
		Contact contact = contactService.create(contactForm);
		this.sendMessage(contact);
		return ResponseEntity.ok("Merci pour votre envoi !");
	}
	
	private void sendMessage(Contact contact) {
		emailService.sendSimpleMessage(
			"contact@chez-soi.com",
			contact.getEmail(),
			"from contact page: " + contact.getEmail(),
			contact.getMessage()
		);
	}
	
}
