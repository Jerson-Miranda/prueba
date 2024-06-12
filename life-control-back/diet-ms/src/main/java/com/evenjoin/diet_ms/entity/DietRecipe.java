package com.evenjoin.diet_ms.entity;

import com.evenjoin.diet_ms.entity.embedded.DietRecipeId;

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
@Table(name = "diet_recipe")
public class DietRecipe {
	
	@EmbeddedId
	private DietRecipeId id;
	
	@ManyToOne
	@MapsId("idDiet")
	@JoinColumn(name = "id_diet", nullable = false)
	private Diet diet;
	
	@ManyToOne
	@MapsId("idRecipe")
	@JoinColumn(name = "id_recipe", nullable = false)
	private Recipe recipe;

	@Column(nullable = false)
	private Integer portion;
	
}
