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
@Table(name = "micronutrient")
public class Micronutrient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_micronutrient")
	private Long idMicronutrient;
	
	@ManyToOne
	@JoinColumn(name = "vitamin", nullable = false)
	private Vitamin vitamin;
	
	@ManyToOne
	@JoinColumn(name = "mineral", nullable = false)
	private Mineral mineral;
	
}
