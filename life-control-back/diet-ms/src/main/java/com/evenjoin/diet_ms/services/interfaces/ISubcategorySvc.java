package com.evenjoin.diet_ms.services.interfaces;

import java.util.List;
import com.evenjoin.diet_ms.entity.Subcategory;

public interface ISubcategorySvc {

	public List<Subcategory> getSubcategories();
	public Subcategory getSubcategory(Long idSubcategory);
	public Subcategory addSubcategory(Subcategory subcategory);
	public void deleteSubcategory(Long idSubcategory);
	public List<Object[]> getSubcategoriesByOwner(String owner);
	
}
