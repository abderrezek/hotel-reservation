package com.abderrezek.hotelreservations.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abderrezek.hotelreservations.entity.Chambre;
import com.abderrezek.hotelreservations.entity.Reservation;

public interface ChambreRepository extends JpaRepository<Chambre, Long>{

	Optional<Chambre> findBySlug(String slug);
	
	List<Chambre> findAllByPersonnesGreaterThanEqualAndReservationsNotIn(int personnes, List<Reservation> reservations);
	
	List<Chambre> findAllByPersonnesGreaterThanEqual(int personnes);
}
