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
@Table(name = "mineral")
public class Mineral {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_mineral")
	private Long idMineral;
	
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal calcium;
	
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal phosphorus;

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal potassium;
	
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal sodium;
	
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal chloride;
	
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal magnesium;
	
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal sulfur;
	
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal iron;
	
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal zinc;
	
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal copper;
	
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal manganese;
	
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal iodine;
	
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal selenium;
	
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal molybdenum;
	
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal cobalt;
	
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal fluoride;
	
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal chromium;
	
}
