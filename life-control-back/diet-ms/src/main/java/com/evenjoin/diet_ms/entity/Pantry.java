package com.evenjoin.diet_ms.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pantry")
public class Pantry {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_pantry")
	private Long idPantry;
	
	@Column(name = "expiration_date", nullable = false)
	private Date expirationDate;
	
	@Column(nullable = false)
	private int stock;
	
	@ManyToOne
	@JoinColumn(name = "ingredient", nullable = false)
	private Ingredient ingredient;
	
}
