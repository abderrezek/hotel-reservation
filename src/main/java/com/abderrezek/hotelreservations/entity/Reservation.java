package com.abderrezek.hotelreservations.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reservations")
public class Reservation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date arrivee;
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date depart;
	@Column(nullable = false)
	private int personnes;
	@Column(nullable = false)
	private boolean habite;
	@Column(nullable = false)
	private double total;
	@Column(nullable = false)
	private String email;
	@Column(nullable = false)
	private String telephone;
	@Column(nullable = false)
	private String nom;
	@Column(nullable = false)
	private String prenom;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "chambre_id", nullable = false)
	private Chambre chambre;

}
