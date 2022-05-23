package com.abderrezek.hotelreservations.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Contact {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String nom;
	@Column(nullable = false)
	private String email;
	@Column(nullable = false)
	private String telephone;
	@Column(nullable = false)
	private String message;
	@Temporal(TemporalType.TIMESTAMP)
	private Date creation;
	
}
