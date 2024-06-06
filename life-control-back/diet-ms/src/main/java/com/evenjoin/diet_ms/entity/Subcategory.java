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
@Table(name = "subcategory")
public class Subcategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_subcategory")
	private Long idSubcategory;
	
	@Column(length = 50, nullable = false)
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "category", nullable = false)
	private Category category;
	
}
