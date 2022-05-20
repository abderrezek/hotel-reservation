package com.abderrezek.hotelreservations.service;

import java.util.Date;
import java.util.List;

import com.abderrezek.hotelreservations.entity.Reservation;

public interface IReservationService {

	List<Reservation> getAllReserveret(Date arrivee, Date depart);
	
	Reservation create(Reservation reservation);
	
}
