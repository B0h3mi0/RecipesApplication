package com.example.RecetarioApp.MV.infrastructure.controllers;

import com.example.RecetarioApp.MV.infrastructure.dtos.recipe.RecipeRequest;
import com.example.RecetarioApp.MV.services.ingredient.IIngredientService;
import com.example.RecetarioApp.MV.services.recipe.IRecipeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/recipes")
@RequiredArgsConstructor
@Slf4j
public class RecipeController {

    private final IIngredientService ingredientService;
    private final IRecipeService recipeService;

    @GetMapping
    public String listRecipes(Model model) {
        model.addAttribute("recipes", recipeService.findAll());
        return "recipes/list";
    }
    @GetMapping("/create")
    public String createRecipe(Model model) {
        model.addAttribute("recipe", new RecipeRequest());
        model.addAttribute("ingredients", ingredientService.findAll());
        return "recipes/form";
    }

    @PostMapping("/save")
    public String saveRecipe(
            @Valid @ModelAttribute("recipe") RecipeRequest recipe,
            BindingResult result,
            Model model) {


        if (result.hasErrors()) {
            log.warn("Errores de validación: {}", result.getAllErrors());
            model.addAttribute("ingredients", ingredientService.findAll());
            return "recipes/form";
        }

        log.info("Datos válidos, guardando receta");
        recipeService.create(recipe);

        log.info("Receta guardada, redirigiendo a /recipes");
        return "redirect:/recipes";
    }

}


