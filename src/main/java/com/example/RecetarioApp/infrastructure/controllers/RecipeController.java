package com.example.RecetarioApp.infrastructure.controllers;

import com.example.RecetarioApp.infrastructure.dtos.recipe.RecipeRequest;
import com.example.RecetarioApp.services.ingredient.IIngredientService;
import com.example.RecetarioApp.services.recipe.IRecipeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;


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
        model.addAttribute("countries", List.of("Argentina", "Chile", "México", "España", "Perú"));
        model.addAttribute("recipeTypes", List.of("Postre", "Entrada", "Plato Principal", "Bebida"));
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

    @GetMapping("/{id}")
    public String viewRecipe(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.findById(id));
        return "recipes/view";
    }

    @GetMapping("/edit/{id}")
    public String editRecipe(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.findById(id));
        model.addAttribute("ingredients", ingredientService.findAll());
        model.addAttribute("countries", List.of("Argentina", "Chile", "México", "España", "Perú"));
        model.addAttribute("recipeTypes", List.of("Postre", "Entrada", "Plato Principal", "Bebida"));

        return "recipes/form";
    }

    @PostMapping("/delete/{id}")
    public String deleteRecipe(@PathVariable Long id) {
        recipeService.delete(id);
        return "redirect:/recipes";
    }


}


