package com.rs.springframework.services;

import com.rs.springframework.commands.RecipeCommand;
import com.rs.springframework.converters.RecipeCommandToRecipe;
import com.rs.springframework.converters.RecipeToRecipeCommand;
import com.rs.springframework.domain.Recipe;
import com.rs.springframework.exceptions.NotFoundException;
import com.rs.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * created by rs 1/2/2022.
 */
@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe,
                             RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("i am in the service");
        Set<Recipe> recipeSet= new HashSet<>();

        recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
        return recipeSet;
    }

    @Override
    public Recipe findById(String id) {
       Optional<Recipe> recipeOptional= recipeRepository.findById(id);
       if(!recipeOptional.isPresent()){
//           throw new RuntimeException("Recipe Not Found");
             throw new NotFoundException("Recipe Not Found. For ID value: " + id);
       }
        return recipeOptional.get();
    }

    @Override
    @Transactional
    public RecipeCommand findCommandById(String id) {

//        return recipeToRecipeCommand.convert(findById(id));
        RecipeCommand recipeCommand = recipeToRecipeCommand.convert(findById(id));

        //enhance command object with id value
        if(recipeCommand.getIngredients() != null && recipeCommand.getIngredients().size() > 0){
            recipeCommand.getIngredients().forEach(rc -> {
                rc.setRecipeId(recipeCommand.getId());
            });
        }

        return recipeCommand;
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);

        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug("Saved RecipeId:" + savedRecipe.getId());
        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    public void deleteById(String idToDelete) {
        recipeRepository.deleteById(idToDelete);
    }
}
