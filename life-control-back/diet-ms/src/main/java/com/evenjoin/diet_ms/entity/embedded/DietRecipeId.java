package com.evenjoin.diet_ms.entity.embedded;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class DietRecipeId implements Serializable {

	private Long idDiet;
	private Long idRecipe;
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DietRecipeId that = (DietRecipeId) o;
        return Objects.equals(idDiet, that.idDiet) &&
                Objects.equals(idRecipe, that.idRecipe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDiet, idRecipe);
    }

	private static final long serialVersionUID = 1L;
}
