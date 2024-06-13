package com.evenjoin.diet_ms.entity;

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
@Table(name = "common_ingredient")
public class CommonIngredient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_common_ingredient")
	private Long idCommonIngredient;
	
	@Column(length = 50, nullable = false)
	private String brand;
	
	@Column(length = 100, nullable = false)
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "macronutrient", nullable = false)
	private Macronutrient macronutrient;
	
	@ManyToOne
	@JoinColumn(name = "micronutrient", nullable = false)
	private Micronutrient micronutrient;
	
	@ManyToOne
	@JoinColumn(name = "subcategory", nullable = false)
	private Subcategory subcategory;
	
}
