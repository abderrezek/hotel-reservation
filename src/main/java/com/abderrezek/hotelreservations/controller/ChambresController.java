package com.abderrezek.hotelreservations.controller;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abderrezek.hotelreservations.entity.Chambre;
import com.abderrezek.hotelreservations.form.SearchForm;
import com.abderrezek.hotelreservations.service.IChambreService;
import com.abderrezek.hotelreservations.service.IReservationService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class ChambresController {

	private final IChambreService chambreService;
	private final IReservationService reservationService;

	/**
	 * Get All Chambres
	 */
	@GetMapping(path = "/chambres")
	public Object index(Pageable pageable, Model model, HttpSession session, HttpServletRequest request) {		
		boolean fromAccueil = false;
		// form for search from acceuil or itself
		SearchForm searchForm = new SearchForm(null, null, 1, 0);
		if (session.getAttribute("searchForm") != null) {
			searchForm = (SearchForm) session.getAttribute("searchForm");
			fromAccueil = true;
		}
		model.addAttribute("fromAccueil", fromAccueil);
		model.addAttribute("chambre", searchForm);
		// from accueil
		Page<Chambre> chambres = null;
		if (session.getAttribute("chambres") != null) {
			// get all chambres not entred
			List<Chambre> listChambres = (List<Chambre>) session.getAttribute("chambres");
			chambres = new PageImpl<>(listChambres, pageable, listChambres.size());
		} else {
			chambres = chambreService.getAll(pageable);
		}
		model.addAttribute("chambres", chambres);
		int max = (int) Math.ceil((double) chambres.getTotalElements() / 5);
		model.addAttribute("max", max);
		
		boolean isAjax = isAjax(request);
		if (isAjax) {
			return "ajax/list-chambres";
		}
		return "chambres";
		/*boolean fromAccueil = false;
		// form for search from acceuil or itself
		SearchForm searchForm = new SearchForm(null, null, 1, 0);
		if (model.getAttribute("searchForm") != null) {
			searchForm = (SearchForm) model.getAttribute("searchForm");
			fromAccueil = true;
		}
		model.addAttribute("fromAccueil", fromAccueil);
		model.addAttribute("chambre", searchForm);
		// from accueil
		Page<Chambre> chambres = null;
		if (model.getAttribute("chambres") != null) {
			// get all chambres not entred
			List<Chambre> listChambres = (List<Chambre>) model.getAttribute("chambres");
			chambres = new PageImpl<>(listChambres, pageable, listChambres.size());
		} else {
			chambres = chambreService.getAll(pageable);
		}
		model.addAttribute("chambres", chambres);
		int max = (int) Math.ceil((double) chambres.getTotalElements() / 5);
		model.addAttribute("max", max);
		
		boolean isAjax = isAjax(request);
		if (isAjax) {
			return "ajax/list-chambres";
		}
		return "chambres";*/
	}
	
	/**
	 * Get detail Chambre
	 */
	@GetMapping(path = "/chambre/{slug}")
	public String show(@PathVariable String slug, Model model, HttpServletRequest request) {
		Chambre chambre = chambreService.getBySlug(slug).orElse(null);
		boolean isAjax = isAjax(request);
		
		model.addAttribute("chambreForm", new SearchForm(null, null, 1, 0));
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
	 * Get prix chambre
	 */
	@PostMapping(path = "/chambre/{slug}/calc")
	public String getPrixReserver(
		@PathVariable String slug,
		HttpServletRequest request,
		Model model,
		@Validated @ModelAttribute SearchForm searchForm,
		BindingResult bindingResult
	) {
		Chambre chambre = chambreService.getBySlug(slug).orElse(null);
		boolean isAjax = isAjax(request);
		
		if (chambre == null && isAjax) {
			return "ajax/reserver :: noChambre";
		}
		if (bindingResult.hasErrors() && isAjax) {
			model.addAttribute("errors", bindingResult.getAllErrors());
			return "ajax/reserver :: faild";
		}
		// calc days between two date
		long joursInMills = Math.abs(searchForm.getArrivee().getTime() - searchForm.getDepart().getTime());
		long jours = TimeUnit.DAYS.convert(joursInMills, TimeUnit.MILLISECONDS);
		
		// calc prix jours
		double prixJours = jours * chambre.getPrix();
		
		// calc total
		double total = prixJours + 52.50;
		
		model.addAttribute("jours", jours);
		model.addAttribute("prixJours", String.format("%.2f", prixJours));
		model.addAttribute("total", String.format("%.2f", total));
		return "ajax/reserver :: success";
	}
	
	/**
	 * reserver
	 */
	@PostMapping(path = "/chambre/{slug}")
	public String reserver(
		@PathVariable String slug,
		@Validated @ModelAttribute("chambreForm") SearchForm searchForm,
		BindingResult bindingResult,
		Model model,
		HttpSession session
	) {
		Chambre chambre = chambreService.getBySlug(slug).orElse(null);
		
		if (chambre == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		model.addAttribute("chambre", chambre);
		model.addAttribute("chambreForm", searchForm);
		if (bindingResult.hasErrors()) {
			return "chambre-details";
		}
		// calc days between two date
		long joursInMills = Math.abs(searchForm.getArrivee().getTime() - searchForm.getDepart().getTime());
		long jours = TimeUnit.DAYS.convert(joursInMills, TimeUnit.MILLISECONDS);
		// calc prix jours
		double prixJours = jours * chambre.getPrix();	
		// calc total
		double total = prixJours + 52.50;
		// go to order page
		session.setAttribute("ADULTES", searchForm.getAdultes());
		session.setAttribute("JOURS", jours);
		session.setAttribute("SOUS_TOTAL", prixJours);
		session.setAttribute("TOTAL", total);
		session.setAttribute("SEARCH_FORM", searchForm);
		session.setAttribute("SLUG", slug);
		return "redirect:/order";
	}
	
	/**
	 * Search Form Chambres
	 */
	@PostMapping(path = "/chambres")
	public String search(
		@Validated @ModelAttribute("chambre") SearchForm searchForm,
		BindingResult bindingResult,
		HttpServletRequest request
	) {
		boolean isAjax = isAjax(request);
		if (bindingResult.hasErrors()) {
			if (isAjax) {
				bindingResult.getAllErrors().forEach(err -> {
					System.out.println(err.getDefaultMessage());
				});
				return "ajax/list-chambres";
			}
			return "redirect:/chambres";
		}
		System.out.println("no error");
		System.out.println(searchForm);
		
		if (isAjax) {
			return "ajax/list-chambres";
		}
		return "redirect:/chambres";
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
