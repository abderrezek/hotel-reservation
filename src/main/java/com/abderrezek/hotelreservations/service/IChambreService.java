package com.abderrezek.hotelreservations.service;

import java.util.List;
import java.util.Optional;

import com.abderrezek.hotelreservations.entity.Chambre;

public interface IChambreService {
	
	List<Chambre> getAll();
	Optional<Chambre> getBySlug(String slug);
	
	Chambre create(Chambre chambre);	

}
