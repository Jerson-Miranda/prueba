package com.evenjoin.diet_ms.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "nutritional_fact")
public class NutritionalFact {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_nutricional_fact")
	private Long idNutricionalFact;
	
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal kcal;
	
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal protein;
	
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal carbohydrate;
	
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal sugar;
	
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal addedSugar;
	
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal fat;
	
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal saturatedFat;
	
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal trans;
	
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal fiber;
	
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal sodium;
	
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal portion;
}
