package com.abderrezek.hotelreservations.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {
	
	@GetMapping
	public String index() {
		return "home";
	}
	
	@PostMapping
	public String search() {
		return "redirect:/chambres";
	}

}
