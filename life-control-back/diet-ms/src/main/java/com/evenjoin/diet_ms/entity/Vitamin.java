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
@Table(name = "vitamin")
public class Vitamin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_vitamin")
	private Long idVitamin;
	
	@Column(name = "vitamin_a", nullable = false, precision = 10, scale = 2)
	private BigDecimal vitaminA;
	
	@Column(name = "vitamin_b", nullable = false, precision = 10, scale = 2)
	private BigDecimal vitaminB;
	
	@Column(name = "vitamin_c", nullable = false, precision = 10, scale = 2)
	private BigDecimal vitaminC;
	
	@Column(name = "vitamin_d", nullable = false, precision = 10, scale = 2)
	private BigDecimal vitaminD;
	
	@Column(name = "vitamin_e", nullable = false, precision = 10, scale = 2)
	private BigDecimal vitaminE;
	
	@Column(name = "vitamin_k", nullable = false, precision = 10, scale = 2)
	private BigDecimal vitaminK;
	
}
