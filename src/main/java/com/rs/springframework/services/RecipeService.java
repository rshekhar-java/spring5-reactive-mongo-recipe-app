package com.rs.springframework.services;

import com.rs.springframework.commands.RecipeCommand;
import com.rs.springframework.domain.Recipe;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

/**
 * created by rs 1/2/2022.
 */
public interface RecipeService {
    Flux<Recipe> getRecipes();
    Mono<Recipe> findById(String id);
    Mono<RecipeCommand> findCommandById(String id);
    Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command);
    Mono<Void> deleteById(String idToDelete);

}
