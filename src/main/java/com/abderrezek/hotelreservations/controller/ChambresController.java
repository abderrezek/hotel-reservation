package com.abderrezek.hotelreservations.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	public String index(Pageable pageable, Model model, HttpServletRequest request) {
		//List<Chambre> chambres = chambreService.getAll();
		Page<Chambre> chambres = chambreService.getAll(pageable);
		boolean isAjax = isAjax(request);
		model.addAttribute("chambres", chambres);
		int max = (int) Math.ceil(chambres.getTotalElements() / 5);
		model.addAttribute("max", max);
		if (isAjax) {
			return "ajax/list-chambres";
		}
		return "chambres";
	}
	
	@GetMapping(path = "/chambre/{slug}")
	public String show(@PathVariable String slug, Model model, HttpServletRequest request) {
		Chambre chambre = chambreService.getBySlug(slug).orElse(null);
		boolean isAjax = isAjax(request);
		
		if (chambre == null) {
			if (isAjax) {
				return "ajax/chambre :: faild";
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND);
			}
		}
		model.addAttribute("chambre", chambre);
		return isAjax ? "ajax/chambre :: success" : "chambre-details";
	}
	
	/**
	 * check if request is ajax call
	 * @param request HttpServletRequest
	 * @return boolean
	 */
	private boolean isAjax(HttpServletRequest request) {
		String requestedWithHeader = request.getHeader("X-Requested-With");
		return "XMLHttpRequest".equals(requestedWithHeader);
	}

}
