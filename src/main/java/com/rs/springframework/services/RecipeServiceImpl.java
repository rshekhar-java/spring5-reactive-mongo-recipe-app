package com.rs.springframework.services;

import com.rs.springframework.commands.RecipeCommand;
import com.rs.springframework.converters.RecipeCommandToRecipe;
import com.rs.springframework.converters.RecipeToRecipeCommand;
import com.rs.springframework.domain.Recipe;
import com.rs.springframework.exceptions.NotFoundException;
import com.rs.springframework.repositories.RecipeRepository;
import com.rs.springframework.repositories.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * created by rs 1/2/2022.
 */
@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeReactiveRepository recipeReactiveRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeReactiveRepository recipeReactiveRepository, RecipeCommandToRecipe recipeCommandToRecipe,
                             RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeReactiveRepository = recipeReactiveRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Flux<Recipe> getRecipes() {
        log.debug("i am in the service");
        return recipeReactiveRepository.findAll();
    }

    @Override
    public Mono<Recipe> findById(String id) {
        return recipeReactiveRepository.findById(id);
/*
        Optional<Recipe> recipeOptional= recipeRepository.findById(id);
       if(!recipeOptional.isPresent()){
//           throw new RuntimeException("Recipe Not Found");
             throw new NotFoundException("Recipe Not Found. For ID value: " + id);
       }
        return recipeOptional.get();
*/
    }

    @Override
    @Transactional
    public Mono<RecipeCommand> findCommandById(String id) {

        return recipeReactiveRepository.findById(id)
                .map(recipe -> {
                    RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);
                    recipeCommand.getIngredients().forEach(rc ->{
                        rc.setRecipeId(recipeCommand.getId());
                    });
                    return recipeCommand;
                });
    }

    @Override
    @Transactional
    public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command) {
        return recipeReactiveRepository.save(recipeCommandToRecipe.convert(command))
                .map(recipeToRecipeCommand::convert);
    }

    @Override
    public Mono<Void> deleteById(String idToDelete) {
        recipeReactiveRepository.deleteById(idToDelete).block();
        return Mono.empty();
    }
}
