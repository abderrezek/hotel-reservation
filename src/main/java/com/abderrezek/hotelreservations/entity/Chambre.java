package com.abderrezek.hotelreservations.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "chambres")
public class Chambre implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, unique = true)
	private String nom;
	@Column(nullable = false, unique = true)
	private String slug;
	@Column(nullable = false)
	private double prix;
	@Column(nullable = false)
	private int personnes;
	@Column(nullable = false)
	private double taille;
	@Column(nullable = false)
	private int lits;
	@Column(nullable = false)
	@Embedded
	private Confort confort;
	private String description;
	@ElementCollection
	private List<String> images = new ArrayList<>();
	
	@OneToMany(mappedBy = "chambre", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Collection<Reservation> reservations;

}
