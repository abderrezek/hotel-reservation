package com.abderrezek.hotelreservations.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abderrezek.hotelreservations.entity.Chambre;
import com.abderrezek.hotelreservations.entity.Reservation;
import com.abderrezek.hotelreservations.form.SearchForm;
import com.abderrezek.hotelreservations.service.IChambreService;
import com.abderrezek.hotelreservations.service.IReservationService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class HomeController {
	
	private final IReservationService reservationService;
	private final IChambreService chambreService;
	
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
		// get all chambres >= personnes: (adultes + enfants)
		List<Chambre> listChambres = chambreService
				.getAllPersonnesSatisfait(searchForm.getAdultes() + searchForm.getEnfants());
		// get all chambres reservet: between date arrivee & depart
		List<Reservation> listReservations = reservationService
				.getAllReserveret(searchForm.getArrivee(), searchForm.getDepart());
		List<Long> reservations = listReservations.stream()
				.map(r -> r.getChambre().getId())
				.toList();
		
		List<Chambre> chambres = listChambres.stream()
				.filter(c -> !reservations.contains(c.getId()))
				.toList();

		// pass variable
		redirectAttributes.addFlashAttribute("chambres", chambres);
		redirectAttributes.addFlashAttribute("searchForm", searchForm);
		return "redirect:/chambres";
	}

}
