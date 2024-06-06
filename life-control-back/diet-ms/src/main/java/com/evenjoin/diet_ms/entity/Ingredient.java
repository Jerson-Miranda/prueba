package com.evenjoin.diet_ms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ingredient")
public class Ingredient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_ingredient")
	private Long idIngredient;
	
	@Column(length = 15, nullable = false, unique = true)
	private String barcode;
	
	@Column(length = 50, nullable = false)
	private String brand;
	
	@Column(nullable = false)
	private String name;
	
	@Lob
	@Column(nullable = false)
	private String photo;
	
	@Lob
	@Column(nullable = false)
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "nutritional_fact", nullable = false)
	private NutritionalFact nutritionalFact;
	
	@ManyToOne
	@JoinColumn(name = "subcategory", nullable = false)
	private Subcategory subcategory;
	
	@ManyToOne
	@JoinColumn(name = "type_ingredient", nullable = false)
	private TypeIngredient typeIngredient;
	
}
