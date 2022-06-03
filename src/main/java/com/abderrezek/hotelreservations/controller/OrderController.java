package com.abderrezek.hotelreservations.controller;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.abderrezek.hotelreservations.entity.Chambre;
import com.abderrezek.hotelreservations.entity.Reservation;
import com.abderrezek.hotelreservations.form.OrderForm;
import com.abderrezek.hotelreservations.form.SearchForm;
import com.abderrezek.hotelreservations.service.ChambreService;
import com.abderrezek.hotelreservations.service.IChambreService;
import com.abderrezek.hotelreservations.service.IReservationService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
@RequestMapping(path = "/order")
public class OrderController {
	
	private final IChambreService chambreService;
	private final IReservationService reservationService;

	@GetMapping
	public String index(Model model, HttpSession session) {
		// get data from session
		SearchForm searchForm = (SearchForm) session.getAttribute("SEARCH_FORM");
		String slug = (String) session.getAttribute("SLUG");
		if (searchForm == null || slug == null) {
			return "redirect:/chambres";
		}
		model.addAttribute("order", new OrderForm("", "", "", "", ""));
		return "order";
	}
	
	@PostMapping
	public String continueOrder(
		@Validated @ModelAttribute("order") OrderForm orderForm,
		BindingResult bindingResult,
		HttpSession session
	) {
		if (bindingResult.hasErrors()) {
			return "order";
		}
		Reservation reservation = new Reservation();
		reservation.setEmail(orderForm.getEmail());
		reservation.setTelephone(orderForm.getTelephone());
		reservation.setNom(orderForm.getNom());
		reservation.setPrenom(orderForm.getPrenom());
		int personnes = (int) session.getAttribute("ADULTES");
		reservation.setPersonnes(personnes);
		reservation.setHabite(false);
		SearchForm searchForm = (SearchForm) session.getAttribute("SEARCH_FORM");
		reservation.setArrivee(searchForm.getArrivee());
		reservation.setDepart(searchForm.getDepart());
		reservation.setTotal((double) session.getAttribute("TOTAL"));
		// get chambre data
		Chambre chambre = chambreService.getBySlug((String) session.getAttribute("SLUG")).orElse(null);		
		if (chambre == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		reservation.setChambre(chambre);
		Reservation reservationPersist = reservationService.create(reservation);
		session.setAttribute("RESERVATION_ID", reservationPersist.getId());
		return "redirect:/felicitaion";
	}
	
}
