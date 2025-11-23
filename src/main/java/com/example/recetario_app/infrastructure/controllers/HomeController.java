package com.example.recetario_app.infrastructure.controllers;


import com.example.recetario_app.services.recipe.IRecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;





@Controller
@RequiredArgsConstructor
public class HomeController {
        private final IRecipeService recipeService;
        @GetMapping({"/", "/home"})
        public String home() {
                return "home";
        }
}
