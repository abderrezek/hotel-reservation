package com.abderrezek.hotelreservations.entity;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Confort {

	private boolean airConditionne;
	private boolean wifi;
	private boolean telephone;
	private boolean television;
	private boolean cuisine;
	private boolean bureau;
	private boolean douche;
	
}
