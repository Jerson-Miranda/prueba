package com.evenjoin.diet_ms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.evenjoin.diet_ms.entity.Subcategory;

import feign.Param;

public interface SubcategoryRepo extends JpaRepository<Subcategory, Long> {

	//Count subcategories
	@Query(
			"SELECT COUNT(sc) " +
			"FROM Subcategory sc"
	)
	public Integer countSubcategories();
		
	//Get subcategories by recipe book
	@Query(
			"SELECT sc " +
			"FROM Subcategory sc " +
			"JOIN sc.category c " +
			"JOIN c.recipeBook rb " +
			"WHERE rb.idRecipeBook = :idRecipeBook"
	)
	public List<Subcategory> getSubcategoriesByRecipeBook(@Param("idRecipeBook") Long idRecipeBook);
	
	//Get subcategories by category
	@Query(
			"SELECT sc " +
			"FROM Subcategory sc " +
			"JOIN sc.category c " +
			"WHERE c.idCategory = :idCategory"
	)
	public List<Subcategory> getSubcategoriesByCategory(@Param("idCategory") Long idCategory);
	
}
