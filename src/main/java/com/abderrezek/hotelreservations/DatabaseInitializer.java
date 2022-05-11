package com.abderrezek.hotelreservations;

import java.util.Locale;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.abderrezek.hotelreservations.entity.Chambre;
import com.abderrezek.hotelreservations.entity.Confort;
import com.abderrezek.hotelreservations.service.IChambreService;
import com.github.javafaker.Faker;
import com.github.slugify.Slugify;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
@Profile("init-db")
public class DatabaseInitializer implements CommandLineRunner {

	private final Slugify slugify = new Slugify();
	private final Faker faker = new Faker(new Locale("fr"));
	private final IChambreService chambreService;
	
	@Override
	public void run(String... args) throws Exception {
		Chambre chambre;
		for (int i = 0; i < 20; i++) {
			chambre = randomChambre();
			chambreService.create(chambre);
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
		String description = faker.lorem().paragraph();
		return new Chambre(null, nom, slug, prix, personnes, taille, lits, confort, description);
	}

}
