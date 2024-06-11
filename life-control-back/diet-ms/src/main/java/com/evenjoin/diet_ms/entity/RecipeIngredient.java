package com.evenjoin.diet_ms.entity;

import com.evenjoin.diet_ms.entity.embedded.RecipeIngredientId;

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
@Table(name = "recipe_ingredient")
public class RecipeIngredient {

	@EmbeddedId
	private RecipeIngredientId id;
	
	@ManyToOne
	@MapsId("idRecipe")
	@JoinColumn(name = "id_recipe", nullable = false)
	private Recipe recipe;
	
	@ManyToOne
	@MapsId("idIngredient")
	@JoinColumn(name ="id_ingredient", nullable = false)
	private Ingredient ingredient;
	
	@Column(nullable = false)
	private int amount;
}
