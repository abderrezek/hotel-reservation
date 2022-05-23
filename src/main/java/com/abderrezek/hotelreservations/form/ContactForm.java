package com.abderrezek.hotelreservations.form;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import com.abderrezek.hotelreservations.entity.Contact;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactForm {
	
	private Long id;
	@NotBlank
	private String nom;
	@NotBlank
	private String email;
	@NotBlank
	private String telephone;
	@NotBlank
	private String message;
	
	public Contact toEntity() {
		return new Contact(
			this.getId(),
			this.getNom(),
			this.getEmail(),
			this.getTelephone(),
			this.getMessage(),
			new Date()
		);
	}

}
