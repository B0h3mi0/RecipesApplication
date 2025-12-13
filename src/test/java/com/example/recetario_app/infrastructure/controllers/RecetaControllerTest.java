package com.example.recetario_app.infrastructure.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(RecetaController.class)
class RecetaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void mostrarBuscador_ShouldReturnBuscaRecetaView() throws Exception {
        mockMvc.perform(get("/busca-receta"))
                .andExpect(status().isOk())
                .andExpect(view().name("busca-receta"));
    }
}
