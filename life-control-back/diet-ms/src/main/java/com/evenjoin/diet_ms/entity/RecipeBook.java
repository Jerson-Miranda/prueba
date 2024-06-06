package com.evenjoin.diet_ms.entity;

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
@Table(name = "recipe_book")
public class RecipeBook {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_recipe_book")
	private Long idRecipeBook;
	
	@Column(length = 100, nullable = false, unique = true)
	private String name;
	
	@Lob
	@Column(nullable = false)
	private String procedure_text;
	
	@Lob
	@Column(nullable = false)
	private String photo;

	@Column(name = "time_minute", nullable = false)
	private int timeMinute;
	
	@ManyToOne
	@JoinColumn(name = "subcategory", nullable = false)
	private Subcategory subcategory;
	
}
