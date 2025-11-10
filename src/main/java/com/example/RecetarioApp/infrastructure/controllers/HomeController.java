package com.example.RecetarioApp.infrastructure.controllers;


import com.example.RecetarioApp.services.recipe.IRecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;





@Controller
@RequiredArgsConstructor
public class HomeController {
        private final IRecipeService recipeService;
        @GetMapping({"/", "/home"})
        public String home(Model model) {
                model.addAttribute("recetasPopulares", recipeService.findPopulars());
                model.addAttribute("recetasRecientes", recipeService.findRecent());
                return "home";
        }
}
