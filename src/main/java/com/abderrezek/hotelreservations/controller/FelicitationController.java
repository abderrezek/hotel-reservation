package com.abderrezek.hotelreservations.controller;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.abderrezek.hotelreservations.entity.Reservation;
import com.abderrezek.hotelreservations.service.IReservationService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
@RequestMapping(path = "/felicitaion")
public class FelicitationController {
	
	private final IReservationService reservationService;
	
	@GetMapping
	public String index(Model model, HttpSession session) {
		Long id = (Long) session.getAttribute("RESERVATION_ID");
		if (id == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		Reservation reservation = reservationService.findById(id).orElse(null);
		if (reservation == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		model.addAttribute("reservation", reservation);
		return "felicitation";
	}
	
	@GetMapping(path = "/download")
	public void exportPDF() {
		
	}
	
}
