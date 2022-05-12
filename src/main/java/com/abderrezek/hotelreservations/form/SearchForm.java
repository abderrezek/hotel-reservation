package com.abderrezek.hotelreservations.form;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchForm {
	
	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date arrivee;
	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date depart;
	@Min(1)
	private int adultes;
	@Min(0)
	private int enfants;

}
