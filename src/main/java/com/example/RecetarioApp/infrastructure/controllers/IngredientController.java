package com.example.RecetarioApp.infrastructure.controllers;

import com.example.RecetarioApp.domain.entities.recipe.IngredientEntity;
import com.example.RecetarioApp.infrastructure.dtos.ingredient.IngredientRequest;
import com.example.RecetarioApp.services.ingredient.IIngredientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/ingredients")
@RequiredArgsConstructor
public class IngredientController {

    private final IIngredientService ingredientService;

    @GetMapping
    public String listIngredients(Model model) {
        model.addAttribute("ingredients", ingredientService.findAll());
        return "ingredients/list";
    }

    @GetMapping("/create")
    public String createIngredient(Model model) {
        model.addAttribute("ingredient", new IngredientEntity());
        return "ingredients/form";
    }

    @PostMapping("/save")
    public String saveIngredient(@Valid @ModelAttribute("ingredient") IngredientRequest ingredient,
                                 BindingResult result,
                                 Model model) {

        if (result.hasErrors()) {
            return "ingredients/form";
        }

        ingredientService.create(ingredient);
        return "redirect:/ingredients";
    }

    @GetMapping("/edit/{id}")
    public String editIngredient(@PathVariable Integer id, Model model) {
        model.addAttribute("ingredient", ingredientService.findById(id));
        return "ingredients/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteIngredient(@PathVariable Integer id) {
        ingredientService.delete(id);
        return "redirect:/ingredients";
    }
}
