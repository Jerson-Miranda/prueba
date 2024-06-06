package com.evenjoin.diet_ms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.evenjoin.diet_ms.entity.Subcategory;

import feign.Param;

public interface SubcategoryRepo extends JpaRepository<Subcategory, Long> {

	//Get subcategories by owner
	@Query(
			"SELECT sc.idSubcategory, sc.name " +
			"FROM Subcategory sc " +
			"JOIN sc.category c " +
			"WHERE c.owner = :owner"
	)
	public List<Object[]> getSubcategoriesByOwner(@Param("owner") String owner);
	
}
