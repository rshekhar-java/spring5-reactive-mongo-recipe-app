package com.rs.springframework.controllers;

import com.rs.springframework.commands.IngredientCommand;
import com.rs.springframework.commands.RecipeCommand;
import com.rs.springframework.commands.UnitOfMeasureCommand;
import com.rs.springframework.services.IngredientService;
import com.rs.springframework.services.RecipeService;
import com.rs.springframework.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * created by rs 1/12/2022.
 */
@Slf4j
@Controller
public class IngredientController {
    private final IngredientService ingredientService;
    private final RecipeService recipeService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(IngredientService ingredientService,RecipeService recipeService,
                                UnitOfMeasureService unitOfMeasureService) {
        this.ingredientService = ingredientService;
        this.recipeService = recipeService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("/recipe/{recipeId}/ingredients")
    public String listIngredients(@PathVariable String recipeId, Model model){
        log.debug("Getting ingredient list for recipe id: "+recipeId);

        //use command object to avoid lazy load errors in thymeleaf
        model.addAttribute("recipe",recipeService.findCommandById(recipeId));
        return "recipe/ingredient/list";

    }

    @GetMapping("recipe/{recipeId}/ingredient/{id}/show")
    public String showRecipeIngredient(@PathVariable String recipeId,
                                       @PathVariable String id, Model model) {

        model.addAttribute("ingredient",
                ingredientService.findByRecipeIdAndIngredientId(recipeId,id));
        return "recipe/ingredient/show";
    }
    @GetMapping("recipe/{recipeId}/ingredient/new")
    public String newIngredient(@PathVariable String recipeId, Model model){
        //make sure we have good id value
        RecipeCommand recipeCommand = recipeService.findCommandById(recipeId);
        //todo raise exception if null

        //need to retrun back parent id for hidden from property
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(recipeId);
        model.addAttribute("ingredient",ingredientCommand);

        //init uom
        ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());

        model.addAttribute("uomList",unitOfMeasureService.listAllUoms());
        return "recipe/ingredient/ingredientform";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{id}/update")
    public String updateRecipeIngredient(@PathVariable String recipeId, @PathVariable String id,Model model){
        model.addAttribute("ingredient",
                ingredientService.findByRecipeIdAndIngredientId(recipeId,id));
        model.addAttribute("uomList",unitOfMeasureService.listAllUoms());
        return "recipe/ingredient/ingredientform";

    }

    @PostMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand command){
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);
        log.debug("save recipe id: "+savedCommand.getRecipeId());
        log.debug("Save ingredient id: "+savedCommand.getId());

        return "redirect:/recipe/"+ savedCommand.getRecipeId()+"/ingredient/"+savedCommand.getId()+"/show";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{id}/delete")
    public String deleteIngredient(@PathVariable String recipeId, @PathVariable String id){

        log.debug("deletuing ingredient id: "+id);
        ingredientService.deleteById(recipeId,id);

        return "redirect:/recipe/"+recipeId+"/ingredients";


    }
}
