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
@Table(name = "vitamin_k")
public class VitaminK {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_vitamin_k")
	private Long idVitaminK;
	
	@Column(name = "vitamin_k1", nullable = false, precision = 10, scale = 2)
	private BigDecimal vitaminK1;

    @Column(name = "vitamin_k2", nullable = false, precision = 10, scale = 2)
	private BigDecimal vitaminK2;
    
}
