package com.rs.springframework.services;

import com.rs.springframework.commands.RecipeCommand;
import com.rs.springframework.domain.Recipe;

import java.util.Set;

/**
 * created by rs 1/2/2022.
 */
public interface RecipeService {
    Set<Recipe> getRecipes();
    Recipe findById(String id);
    RecipeCommand findCommandById(String id);
    RecipeCommand saveRecipeCommand(RecipeCommand command);
    void deleteById(String idToDelete);

}
