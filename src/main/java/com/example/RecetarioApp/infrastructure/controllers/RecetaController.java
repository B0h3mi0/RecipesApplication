package com.example.RecetarioApp.infrastructure.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RecetaController {
    @GetMapping("/busca-receta")
    public String mostrarBuscador() {
        return "busca-receta";
    }
}
