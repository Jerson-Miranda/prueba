package com.evenjoin.diet_ms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.evenjoin.diet_ms.entity.RecipeBook;

import feign.Param;

public interface RecipeBookRepo extends JpaRepository<RecipeBook, Long> {

    // Count recipe books
    @Query("SELECT COUNT(rb) " +
            "FROM RecipeBook rb")
    public Integer countRecipeBooks();

    // Get recipe book by recipe
    @Query("SELECT rb " +
            "FROM RecipeBook rb " +
            "JOIN Category c ON c.recipeBook.idRecipeBook = rb.idRecipeBook " +
            "JOIN Subcategory sc ON sc.category.idCategory = c.idCategory " +
            "JOIN Recipe r ON r.subcategory.idSubcategory = sc.idSubcategory " +
            "WHERE r.idRecipe = :idRecipe")
    public RecipeBook getRecipeBookByRecipe(@Param("idRecipe") Long idRecipe);

    // Get favorite recipes
    @Query("SELECT rb " +
            "FROM RecipeBook rb " +
            "WHERE rb.isFavorite = true")
    public List<RecipeBook> getFavoriteRecipeBooks();
    
}
