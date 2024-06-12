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
@Table(name = "recipe")
public class Recipe {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_recipe")
	private Long idRecipe;
	
	@Column(length = 100, nullable = false, unique = true)
	private String name;
	
	@Lob
	@Column(nullable = false)
	private String procedure_text;
	
	@Lob
	@Column(nullable = false)
	private String photo;

	@Column(name = "time_minute", nullable = false)
	private Integer timeMinute;
	
	@Column(name = "is_favorite", nullable = false)
	private Boolean isFavorite;
	
	@ManyToOne
	@JoinColumn(name = "subcategory", nullable = false)
	private Subcategory subcategory;
	
	@ManyToOne
	@JoinColumn(name = "recipe_book", nullable = false)
	private RecipeBook recipeBook;
	
}
