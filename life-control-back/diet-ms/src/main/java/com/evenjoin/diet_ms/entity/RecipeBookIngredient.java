package com.evenjoin.diet_ms.entity;

import com.evenjoin.diet_ms.entity.embedded.RecipeBookIngredientId;

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
@Table(name = "recipebook_ingredient")
public class RecipeBookIngredient {

	@EmbeddedId
	private RecipeBookIngredientId id;
	
	@ManyToOne
	@MapsId("idRecipeBook")
	@JoinColumn(name = "id_recipe_book", nullable = false)
	private RecipeBook recipebook;
	
	@ManyToOne
	@MapsId("idIngredient")
	@JoinColumn(name ="id_ingredient", nullable = false)
	private Ingredient ingredient;
	
	@Column(nullable = false)
	private int amount;
}
