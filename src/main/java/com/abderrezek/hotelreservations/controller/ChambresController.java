package com.abderrezek.hotelreservations.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import com.abderrezek.hotelreservations.entity.Chambre;
import com.abderrezek.hotelreservations.form.SearchForm;
import com.abderrezek.hotelreservations.service.IChambreService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class ChambresController {

	private final IChambreService chambreService;

	@GetMapping(path = "/chambres")
	public Object index(Pageable pageable, Model model, HttpServletRequest request) {
		SearchForm searchForm = new SearchForm(null, null, 1, 0);
		if (model.getAttribute("chambreForm") != null) {
			searchForm = (SearchForm) model.getAttribute("chambreForm");
		}
		model.addAttribute("chambre", searchForm);
		
		//List<Chambre> chambres = chambreService.getAll();
		Page<Chambre> chambres = chambreService.getAll(pageable);
		model.addAttribute("chambres", chambres);
		
		int max = (int) Math.ceil((double) chambres.getTotalElements() / 5);
		model.addAttribute("max", max);
		
		boolean isAjax = isAjax(request);
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
	
	@PostMapping(path = "/chambres")
	public String search(
		
	) {
		System.out.println("call");
		return "ajax/list-chambres";
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
