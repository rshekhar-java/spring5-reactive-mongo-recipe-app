package com.rs.springframework.services;

import com.rs.springframework.commands.IngredientCommand;

/**
 * created by rs 1/12/2022.
 */
public interface IngredientService {
    IngredientCommand findByRecipeIdAndIngredientId(String recipeId,String ingredientId);
    IngredientCommand saveIngredientCommand(IngredientCommand command);

    void deleteById(String recipeId,String idToDelete);
}
