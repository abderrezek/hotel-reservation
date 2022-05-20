package com.abderrezek.hotelreservations.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.abderrezek.hotelreservations.entity.Chambre;
import com.abderrezek.hotelreservations.entity.Reservation;
import com.abderrezek.hotelreservations.repository.ChambreRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ChambreService implements IChambreService {
	
	private final ChambreRepository chambreRepository;

	@Override
	public List<Chambre> getAll() {
		return chambreRepository.findAll();
	}

	@Override
	public Page<Chambre> getAll(Pageable pageable) {
		return chambreRepository.findAll(pageable);
	}

	@Override
	public Chambre create(Chambre chambre) {
		return chambreRepository.save(chambre);
	}

	@Override
	public Optional<Chambre> getBySlug(String slug) {
		return chambreRepository.findBySlug(slug);
	}

	@Override
	public List<Chambre> getAllPersonnesSatisfait(int personnes) {
		return chambreRepository
				.findAllByPersonnesGreaterThanEqual(personnes);
	}

}
