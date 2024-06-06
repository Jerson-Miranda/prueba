package com.evenjoin.diet_ms.entity;

import com.evenjoin.diet_ms.entity.embedded.DietRecipeBookId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "diet_recipebook")
public class DietRecipeBook {
	
	@EmbeddedId
	private DietRecipeBookId id;
	
	@ManyToOne
	@MapsId("idDiet")
	@JoinColumn(name = "id_diet", nullable = false)
	private Diet diet;
	
	@ManyToOne
	@MapsId("idRecipeBook")
	@JoinColumn(name = "id_recipe_book", nullable = false)
	private RecipeBook recipeBook;

	@Column(nullable = false)
	private int portion;
	
}
