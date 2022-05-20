package com.abderrezek.hotelreservations;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.abderrezek.hotelreservations.entity.Chambre;
import com.abderrezek.hotelreservations.entity.Confort;
import com.abderrezek.hotelreservations.entity.Reservation;
import com.abderrezek.hotelreservations.service.IChambreService;
import com.abderrezek.hotelreservations.service.IReservationService;
import com.github.javafaker.Faker;
import com.github.slugify.Slugify;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
@Profile("init-db")
public class DatabaseInitializer implements CommandLineRunner {

	private final Slugify slugify = new Slugify();
	private final Faker faker = new Faker(new Locale("fr"));
	private static final int DAYS_ADDED = 5;
	private final IChambreService chambreService;
	private final IReservationService reservationService;
	
	@Override
	public void run(String... args) throws Exception {
		List<Chambre> chambres = new ArrayList<>();
		Chambre chambre;
		for (int i = 0; i < 20; i++) {
			chambre = randomChambre();
			chambreService.create(chambre);
			chambres.add(chambre);
		}
		
		Random rand = new Random();
		for (int i = 0; i < 10; i++) {
			chambre = chambres.get(rand.nextInt(chambres.size()));
			reservationService.create(randomReservation(chambre));
		}
		System.out.println("init-db: done.");
	}
	
	private Chambre randomChambre() {
		String nom = faker.company().name();
		String slug = slugify.slugify(nom);
		double prix = Double.parseDouble(faker.commerce().price(4.00, 295.00).replace(',', '.'));
		int personnes = faker.number().randomDigitNotZero();
		double taille = faker.number().randomDouble(0, 20, 100);
		int lits = faker.number().randomDigitNotZero();
		Confort confort = new Confort(
			faker.bool().bool(),
			faker.bool().bool(),
			faker.bool().bool(),
			faker.bool().bool(),
			faker.bool().bool(),
			faker.bool().bool(),
			faker.bool().bool()
		);
		String description = faker.lorem().sentence(10);
		Set<Reservation> reservations = new HashSet<>();
		return new Chambre(null, nom, slug, prix, personnes, taille, lits, confort, description, reservations);
	}
	
	private Reservation randomReservation(Chambre chambre) {
		Date now = new Date();
		
		Calendar ca = Calendar.getInstance();
		ca.setTime(now);
		ca.add(Calendar.DATE, DAYS_ADDED);
		Date arrivee = faker.date().between(now, ca.getTime());
		
		Calendar cd = Calendar.getInstance();
		cd.setTime(arrivee);
		cd.add(Calendar.DATE, DAYS_ADDED);
		Date depart = faker.date().between(arrivee, cd.getTime());
		
		int personnes = faker.number().numberBetween(1, 7);
		boolean habite = faker.bool().bool();
		return new Reservation(null, arrivee, depart, personnes, habite, chambre);
	}

}
