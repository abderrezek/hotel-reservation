package com.abderrezek.hotelreservations.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abderrezek.hotelreservations.entity.Chambre;

public interface ChambreRepository extends JpaRepository<Chambre, Long>{

	Optional<Chambre> findBySlug(String slug);
	
}
