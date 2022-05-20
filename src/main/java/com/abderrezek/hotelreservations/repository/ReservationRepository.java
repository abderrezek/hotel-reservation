package com.abderrezek.hotelreservations.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abderrezek.hotelreservations.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	
	List<Reservation> findAllByArriveeGreaterThanEqualAndDepartLessThanEqualAndHabiteFalse(Date arrivee, Date depart);
	
}
