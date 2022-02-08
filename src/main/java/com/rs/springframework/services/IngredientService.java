package com.rs.springframework.services;

import com.rs.springframework.commands.IngredientCommand;
import reactor.core.publisher.Mono;

/**
 * created by rs 1/12/2022.
 */
public interface IngredientService {
    Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId);
    Mono<IngredientCommand> saveIngredientCommand(IngredientCommand command);

    Mono<Void> deleteById(String recipeId, String idToDelete);
}
