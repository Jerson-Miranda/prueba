package com.evenjoin.diet_ms.entity.embedded;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class RecipeIngredientId implements Serializable {

	private Long idRecipe;
	private Long idIngredient;
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeIngredientId that = (RecipeIngredientId) o;
        return Objects.equals(idRecipe, that.idRecipe) &&
                Objects.equals(idIngredient, that.idIngredient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRecipe, idIngredient);
    }
	
	private static final long serialVersionUID = 1L;

}
