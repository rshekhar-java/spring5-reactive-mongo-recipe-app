package com.rs.springframework.converters;

import com.rs.springframework.commands.IngredientCommand;
import com.rs.springframework.domain.Ingredient;
import com.rs.springframework.domain.Recipe;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * created by rs 1/10/2022.
 */
@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

    private final UnitOfMeasureCommandToUnitOfMeasure uomConverter;

    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure uomConverter) {
        this.uomConverter = uomConverter;
    }

//    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientCommand source) {
        if(source == null){
            return null;
        }

        final Ingredient ingredient = new Ingredient();
        ingredient.setId(source.getId());

        if(source.getRecipeId() != null){
            Recipe recipe = new Recipe();
            recipe.setId(source.getRecipeId());
            recipe.addIngredient(ingredient);
        }
        ingredient.setAmount(source.getAmount());
        ingredient.setDescription(source.getDescription());
        ingredient.setUom(uomConverter.convert(source.getUnitOfMeasure()));
        return ingredient;
    }
}
