package com.evenjoin.diet_ms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.evenjoin.diet_ms.entity.RecipeBook;
import com.evenjoin.diet_ms.repository.RecipeBookRepo;
import com.evenjoin.diet_ms.services.interfaces.IRecipeBookSvc;

@Service
public class RecipeBookSvc implements IRecipeBookSvc {
    
    @Autowired
    private RecipeBookRepo recipeBookRepo;

    @Override
    @Transactional(readOnly = true)
    public List<RecipeBook> getRecipeBooks() {
        return recipeBookRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public RecipeBook getRecipeBook(Long idRecipeBook) {
        return recipeBookRepo.findById(idRecipeBook).orElse(null);
    }

    @Override
    @Transactional()
    public RecipeBook addRecipeBook(RecipeBook recipeBook) {
        return recipeBookRepo.save(recipeBook);
    }

    @Override
    @Transactional()
    public void deleteRecipeBook(Long idRecipeBook) {
        recipeBookRepo.deleteById(idRecipeBook);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer countRecipeBooks() {
        return recipeBookRepo.countRecipeBooks();
    }

    @Override
    @Transactional(readOnly = true)
    public RecipeBook getRecipeBookByRecipe(Long idRecipe) {
        return recipeBookRepo.getRecipeBookByRecipe(idRecipe);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecipeBook> getFavoriteRecipeBooks() {
        return recipeBookRepo.getFavoriteRecipeBooks();
    }

    @Override
    @Transactional(readOnly = true)
    public RecipeBook getMaxConsumptionRecipeBook() {
        return recipeBookRepo.getMaxConsumptionRecipeBook();
    }

    @Override
    @Transactional(readOnly = true)
    public RecipeBook getMinConsumptionRecipeBook() {
        return recipeBookRepo.getMinConsumptionRecipeBook();
    }
}
