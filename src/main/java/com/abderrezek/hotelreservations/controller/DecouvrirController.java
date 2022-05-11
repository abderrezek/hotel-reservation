package com.abderrezek.hotelreservations.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/decouvrir")
public class DecouvrirController {
	
	@GetMapping
	public String index() {
		return "decouvrir";
	}
	
}
