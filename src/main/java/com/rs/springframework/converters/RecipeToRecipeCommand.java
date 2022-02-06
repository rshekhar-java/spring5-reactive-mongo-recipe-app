package com.rs.springframework.converters;

import com.rs.springframework.commands.RecipeCommand;
import com.rs.springframework.domain.Category;
import com.rs.springframework.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * created by rs 1/10/2022.
 */
@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private final CategoryToCategoryCommand categoryConverter;
    private final IngredientToIngredientCommand ingredientConverter;
    private final NotesToNotesCommand notesConverter;

    public RecipeToRecipeCommand(CategoryToCategoryCommand categoryConverter,
                                 IngredientToIngredientCommand ingredientConverter,
                                 NotesToNotesCommand notesConverter) {
        this.categoryConverter = categoryConverter;
        this.ingredientConverter = ingredientConverter;
        this.notesConverter = notesConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe source) {
        if(source == null){
            return null;
        }

        final RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(source.getId());
        recipeCommand.setPrepTime(source.getPrepTime());
        recipeCommand.setCookTime(source.getCookTime());
        recipeCommand.setDescription(source.getDescription());
        recipeCommand.setDifficulty(source.getDifficulty());
        recipeCommand.setDirections(source.getDirections());
        recipeCommand.setImage(source.getImage());
        recipeCommand.setServings(source.getServings());
        recipeCommand.setSource(source.getSource());
        recipeCommand.setUrl(source.getUrl());
        recipeCommand.setNotes(notesConverter.convert(source.getNotes()));

        if (source.getCategories() != null && source.getCategories().size() > 0){
            source.getCategories()
                    .forEach((Category category) -> recipeCommand.getCategories().add(categoryConverter.convert(category)));
        }

        if (source.getIngredients() != null && source.getIngredients().size() > 0){
            source.getIngredients()
                    .forEach(ingredient -> recipeCommand.getIngredients().add(ingredientConverter.convert(ingredient)));
        }
        return recipeCommand;

    }
}
