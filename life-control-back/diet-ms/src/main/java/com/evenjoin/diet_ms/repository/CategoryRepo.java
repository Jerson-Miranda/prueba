package com.evenjoin.diet_ms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.evenjoin.diet_ms.entity.Category;

import feign.Param;

public interface CategoryRepo extends JpaRepository<Category, Long> {

	//Get categories by owner
	@Query(
			"SELECT c.idCategory, c.name " +
			"FROM Category c " +
			"WHERE c.owner = :owner"
	)
	public List<Object[]> getCategoriesByOwner(@Param("owner") String owner);
	
}
