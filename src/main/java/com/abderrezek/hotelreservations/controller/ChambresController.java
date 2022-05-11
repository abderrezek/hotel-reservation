package com.abderrezek.hotelreservations.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import com.abderrezek.hotelreservations.entity.Chambre;
import com.abderrezek.hotelreservations.service.IChambreService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class ChambresController {

	private final IChambreService chambreService;

	@GetMapping(path = "/chambres")
	public String index(Model model) {
		List<Chambre> chambres = chambreService.getAll();
		model.addAttribute("chambres", chambres);
		return "chambres";
	}
	
	@GetMapping(path = "/chambre/{slug}")
	public String show(@PathVariable String slug, Model model) {
		Chambre chambre = chambreService.getBySlug(slug)
				.orElseThrow(() -> new ResponseStatusException(
					HttpStatus.NOT_FOUND
				));

		model.addAttribute("chambre", chambre);
		return "chambre-details";
	}
	
	@GetMapping(path = "/chambre/apercu-rapide/{slug}")
	public String getWithAjax(@PathVariable String slug, Model model) {
		Chambre chambre = chambreService.getBySlug(slug)
				.orElse(null);
		
		if (chambre == null) {
			return "ajax/chambre :: faild";
		}
		model.addAttribute("chambre", chambre);
		return "ajax/chambre :: success";
	}

}
