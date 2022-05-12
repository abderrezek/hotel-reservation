package com.abderrezek.hotelreservations.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abderrezek.hotelreservations.form.SearchForm;

@Controller
public class HomeController {
	
	@GetMapping
	public String index(Model model) {
		model.addAttribute("chambre", new SearchForm(null, null, 1, 0));
		return "home";
	}
	
	@PostMapping
	public String search(
		@Validated @ModelAttribute("chambre") SearchForm searchForm,
		BindingResult bindingResult,
		RedirectAttributes redirectAttributes
	) {
		if (bindingResult.hasErrors()) {
			return "home";
		}
		redirectAttributes.addFlashAttribute("chambreForm", searchForm);
		return "redirect:/chambres";
	}

}
