package com.abderrezek.hotelreservations.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/contact")
public class ContactController {

	@GetMapping
	public String index(Model model) {
//		model.addAttribute("activeNav", "contact");
		return "contact";
	}
	
}
