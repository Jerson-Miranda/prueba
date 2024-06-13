package com.evenjoin.diet_ms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
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
	
	@Column(length = 100, nullable = false)
	private String name;
	
	@Lob
	@Column(nullable = false)
	private String description;
	
	@Lob
	@Column(nullable = false)
	private String photo;
	
	@Lob
	@Column(name = "num_pages", nullable = false)
	private int numPages;
	
	@Column(name = "is_favorite", nullable = false, columnDefinition = "TINYINT(1)")
	private Boolean isFavorite;
	
}
