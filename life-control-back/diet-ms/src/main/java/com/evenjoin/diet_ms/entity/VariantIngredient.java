package com.evenjoin.diet_ms.entity;

import java.math.BigDecimal;

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
@Table(name = "variant_ingredient")
public class VariantIngredient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_variant_ingredient")
	private Long idVariantIngredient;
	
	@Column(name = "gr_ml_pza", nullable = false)
	private Long grMlPza;
	
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal price;
	
	@ManyToOne
	@JoinColumn(name = "ingredient", nullable = false)
	private Ingredient ingredient;
	
}
