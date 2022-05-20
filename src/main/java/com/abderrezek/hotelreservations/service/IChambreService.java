package com.abderrezek.hotelreservations.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.abderrezek.hotelreservations.entity.Chambre;

public interface IChambreService {
	
	List<Chambre> getAllPersonnesSatisfait(int personnes);
	List<Chambre> getAll();
	Page<Chambre> getAll(Pageable pageable);
	
	Optional<Chambre> getBySlug(String slug);
	
	Chambre create(Chambre chambre);	

}
