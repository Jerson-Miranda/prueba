package com.evenjoin.diet_ms.entity.embedded;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class DietRecipeBookId implements Serializable {

	private Long idDiet;
	private Long idRecipeBook;
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DietRecipeBookId that = (DietRecipeBookId) o;
        return Objects.equals(idDiet, that.idDiet) &&
                Objects.equals(idRecipeBook, that.idRecipeBook);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDiet, idRecipeBook);
    }

	private static final long serialVersionUID = 1L;
}
