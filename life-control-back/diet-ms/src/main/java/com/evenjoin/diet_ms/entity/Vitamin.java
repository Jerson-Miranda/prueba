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
@Table(name = "vitamin")
public class Vitamin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_vitamin")
	private Long idVitamin;
	
	@Column(name = "vitamin_a", nullable = false, precision = 10, scale = 2)
	private BigDecimal vitaminA;
	
	@ManyToOne
	@JoinColumn(name = "vitamin_b", nullable = false)
	private VitaminB vitaminB;
	
	@Column(name = "vitamin_c", nullable = false, precision = 10, scale = 2)
	private BigDecimal vitaminC;
	
	@ManyToOne
	@JoinColumn(name = "vitamin_d", nullable = false)
	private VitaminD vitaminD;
	
	@Column(name = "vitamin_e", nullable = false, precision = 10, scale = 2)
	private BigDecimal vitaminE;
	
	@ManyToOne
	@JoinColumn(name = "vitamin_k", nullable = false)
	private VitaminK vitaminK;
	
}
