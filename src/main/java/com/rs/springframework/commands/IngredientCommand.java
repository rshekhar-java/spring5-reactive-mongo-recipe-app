package com.rs.springframework.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * created by rs 1/10/2022.
 */

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {
    private String id;
    private String recipeId;
    private String description;
    private BigDecimal amount;
    private UnitOfMeasureCommand unitOfMeasure;
}
