package com.evenjoin.diet_ms.entity;

import java.math.BigDecimal;

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
@Table(name = "variant_ingredient")
public class VariantIngredient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_variant_ingredient")
	private Long idVariantIngredient;
	
	@Column(length = 15, nullable = false, unique = true)
	private String barcode;
	
	@Lob
	@Column(nullable = false)
	private String photo;
	
	@Lob
	@Column(nullable = false)
	private String description;
	
	@Column(name = "gr_ml_pza", nullable = false, precision = 10, scale = 2)
	private BigDecimal grMlPza;
	
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal price;
	
	@Column(name = "is_favorite", nullable = false)
	private Boolean isFavorite;
	
	@ManyToOne
	@JoinColumn(name = "type_ingredient", nullable = false)
	private TypeIngredient typeIngredient;
	
	@ManyToOne
	@JoinColumn(name = "ingredient", nullable = false)
	private Ingredient ingredient;
	
}
