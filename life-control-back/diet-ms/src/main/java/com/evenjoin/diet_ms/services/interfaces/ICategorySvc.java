package com.evenjoin.diet_ms.services.interfaces;

import java.util.List;
import com.evenjoin.diet_ms.entity.Category;

public interface ICategorySvc {

	public List<Category> getCategories();
	public Category getCategory(Long idCategory);
	public Category addCategory(Category category);
	public void deleteCategory(Long idCategory);
	public List<Object[]> getCategoriesByOwner(String owner);
}
