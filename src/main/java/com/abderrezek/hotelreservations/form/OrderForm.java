package com.abderrezek.hotelreservations.form;

import javax.validation.constraints.NotBlank;

import com.abderrezek.hotelreservations.entity.Chambre;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderForm {

	@NotBlank
	private String prenom;
	@NotBlank
	private String nom;
	@NotBlank
	private String email;
	@NotBlank
	private String telephone;
	private String demandes;
	
}
