package com.evenjoin.diet_ms.entity.embedded;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class RecipeBookIngredientId implements Serializable {

	private Long idRecipeBook;
	private Long idIngredient;
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeBookIngredientId that = (RecipeBookIngredientId) o;
        return Objects.equals(idRecipeBook, that.idRecipeBook) &&
                Objects.equals(idIngredient, that.idIngredient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRecipeBook, idIngredient);
    }
	
	private static final long serialVersionUID = 1L;

}
