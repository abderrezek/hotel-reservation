package com.abderrezek.hotelreservations.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.abderrezek.hotelreservations.entity.Reservation;
import com.abderrezek.hotelreservations.repository.ReservationRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ReservationService implements IReservationService {

	private final ReservationRepository reservationRepository;

	@Override
	public Reservation create(Reservation reservation) {
		return reservationRepository.save(reservation);
	}

	@Override
	public List<Reservation> getAllReserveret(Date arrivee, Date depart) {
		return reservationRepository
				.findAllByArriveeGreaterThanEqualAndDepartLessThanEqualAndHabiteFalse(arrivee, depart);
	}
	
}
