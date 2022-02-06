package com.rs.springframework.services;

import com.rs.springframework.commands.IngredientCommand;
import com.rs.springframework.converters.IngredientCommandToIngredient;
import com.rs.springframework.converters.IngredientToIngredientCommand;
import com.rs.springframework.domain.Ingredient;
import com.rs.springframework.domain.Recipe;
import com.rs.springframework.repositories.RecipeRepository;
import com.rs.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * created by rs 1/12/2022.
 */

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService{

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient,
                                 RecipeRepository recipeRepository,
                                 UnitOfMeasureRepository unitOfMeasureRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(String recipeId, String ingredientId) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if(!recipeOptional.isPresent()){
            //todo impl error handling
            log.error("recipe id not found Id:"+recipeId);
        }
        Recipe recipe = recipeOptional.get();

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();
        if(!ingredientCommandOptional.isPresent()){
            //todo impl error handling
            log.error("Ingredient Id not found : "+ingredientId);
        }
        IngredientCommand ingredientCommand = ingredientCommandOptional.get();
        ingredientCommand.setRecipeId(recipeId);

        return ingredientCommandOptional.get();
    }

    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());

        if(!recipeOptional.isPresent()) {
            //todo toss err if not found
            log.error("Recipe not found for Id: " + command.getRecipeId());
            return new IngredientCommand();
        }else{
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe.getIngredients()
                    .stream()
                    .filter(ingredient ->ingredient.getId().equals(command.getId()))
                    .findFirst();

            if(ingredientOptional.isPresent()){
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(command.getDescription());
                ingredientFound.setAmount(command.getAmount());
                ingredientFound.setUom(unitOfMeasureRepository
                        .findById(command.getUnitOfMeasure().getId())
                        .orElseThrow(()-> new RuntimeException("UOM NOT FOUND")));
            }else{
                //add new Ingredient
//                recipe.addIngredient(ingredientCommandToIngredient.convert(command));
                Ingredient ingredient = ingredientCommandToIngredient.convert(command);
//                ingredient.setRecipe(recipe);
                recipe.addIngredient(ingredient);
            }
            Recipe savedRecipe = recipeRepository.save(recipe);

            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
                    .findFirst();

            //check by description
            if(!savedIngredientOptional.isPresent()){
                //not totally safe... But best guess
                savedIngredientOptional = savedRecipe.getIngredients().stream()
                        .filter(recipeIngredients -> recipeIngredients.getDescription().equals(command.getDescription()))
                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
                        .filter(recipeIngredients -> recipeIngredients.getUom().getId()
                                .equals(command.getUnitOfMeasure().getId()))
                        .findFirst();
            }
//            //to do check for fail
//            return ingredientToIngredientCommand.convert(savedIngredientOptional.get());

//            return ingredientToIngredientCommand.convert(savedRecipe.getIngredients().stream()
//                    .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
//                            .findFirst()
//                            .get());

            //to do check for fail
            //enhance with id value
            IngredientCommand ingredientCommandSaved = ingredientToIngredientCommand.convert(savedIngredientOptional.get());
            ingredientCommandSaved.setRecipeId(recipe.getId());

            return ingredientCommandSaved;
        }
    }

    @Override
    public void deleteById(String recipeId, String idtoDelete) {
        log.debug("Deleting ingredient: "+recipeId+":"+ idtoDelete);
        Optional<Recipe> recipeOptional= recipeRepository.findById(recipeId);

        if(recipeOptional.isPresent()){
            Recipe recipe = recipeOptional.get();
            log.debug("found recipe");

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(idtoDelete))
                    .findFirst();

            if(ingredientOptional.isPresent()){
                log.debug("found ingredient");
                Ingredient ingredientToDelete =ingredientOptional.get();
//                ingredientToDelete.setRecipe(null);
                recipe.getIngredients().remove(ingredientOptional.get());
                recipeRepository.save(recipe);
            }
        }else{
            log.debug("Recipe Id Not Found :"+recipeId);
        }
    }
}
