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
@Table(name = "ingredient")
public class Ingredient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_ingredient")
	private Long idIngredient;
	
	@Column(length = 15, nullable = false, unique = true)
	private String barcode;
	
	@Lob
	@Column(nullable = false, columnDefinition = "LONGTEXT")
	private String photo;
	
	@Lob
	@Column(nullable = false, columnDefinition = "LONGTEXT")
	private String description;
	
	@Column(name = "gr_ml_pza", nullable = false, precision = 10, scale = 2)
	private BigDecimal grMlPza;
	
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal price;
	
	@Column(name = "is_favorite", nullable = false, columnDefinition = "TINYINT(1)")
	private Boolean isFavorite;
	
	@ManyToOne
	@JoinColumn(name = "type_ingredient", nullable = false)
	private TypeIngredient typeIngredient;
	
	@ManyToOne
	@JoinColumn(name = "common_ingredient", nullable = false)
	private CommonIngredient commonIngredient;
	
}
