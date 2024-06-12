package com.evenjoin.diet_ms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.evenjoin.diet_ms.entity.Category;

import feign.Param;

public interface CategoryRepo extends JpaRepository<Category, Long> {
	
	//Count categories
	@Query(
			"SELECT COUNT(c) " +
			"FROM Category c"
	)
	public Integer countCategories();

	//Get categories by recipe book
	@Query(
			"SELECT c " +
			"FROM Category c " +
			"JOIN c.recipeBook rb " +
			"WHERE rb.idRecipeBook = :idRecipeBook"
	)
	public List<Category> getCategoriesByRecipeBook(@Param("idRecipeBook") Long idRecipeBook);
	
}
