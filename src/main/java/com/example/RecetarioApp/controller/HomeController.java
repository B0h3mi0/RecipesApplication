package com.example.RecetarioApp.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.RecetarioApp.domain.Producto;




@Controller
public class HomeController {
        @GetMapping("/home")
        public String home(@RequestParam (name = "name", required = false, defaultValue = "Seguridad y calidad de software") String name, Model model) {
        model.addAttribute("name", "RecetarioApp");
        return "home";
        }

        @GetMapping("/")
        public String root(@RequestParam (name = "name", required = false, defaultValue = "Seguridad y calidad de software") String name, Model model) {
        model.addAttribute("name", "RecetarioApp");
        return "home";
        }

        @GetMapping("/producto")
        public String productos(Model model){
                List<Producto> productos = new ArrayList<>();

                productos.add(new Producto(
                        "Pala de jardín",
                        "Pala pequeña para trasplante.",
                        new BigDecimal("4990"),
                        25,
                        "Herramientas manuales",
                        "Truper"
                ));

                productos.add(new Producto(
                        "Tijera de podar",
                        "Tijera de acero inoxidable con mango ergonómico.",
                        new BigDecimal("8990"),
                        15,
                        "Corte y poda",
                        "Stanley"
                ));

                productos.add(new Producto(
                        "Manguera 20m",
                        "Manguera flexible y resistente a presión.",
                        new BigDecimal("14990"),
                        10,
                        "Riego",
                        "Gardena"
                ));

                productos.add(new Producto(
                        "Cortacésped eléctrico",
                        "Cortacésped de 1200W ideal para jardines medianos.",
                        new BigDecimal("99990"),
                        5,
                        "Maquinaria",
                        "Bosch"
                        ));

                // 🔹 Pasar lista al modelo
                model.addAttribute("productos", productos);

        // 🔹 Retornar la vista de Thymeleaf
        return "productos";
        }
}
