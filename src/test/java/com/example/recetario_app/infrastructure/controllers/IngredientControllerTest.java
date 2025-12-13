package com.example.recetario_app.infrastructure.controllers;

import com.example.recetario_app.infrastructure.dtos.ingredient.IngredientRequest;
import com.example.recetario_app.infrastructure.dtos.ingredient.IngredientResponse;
import com.example.recetario_app.services.ingredient.IIngredientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(IngredientController.class)
class IngredientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IIngredientService ingredientService;

    @Test
    @WithMockUser
    void listIngredients_ShouldReturnListView() throws Exception {
        when(ingredientService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("ingredients/list"))
                .andExpect(model().attributeExists("ingredients"));
    }

    @Test
    @WithMockUser
    void createIngredient_ShouldReturnFormView() throws Exception {
        mockMvc.perform(get("/ingredients/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("ingredients/form"))
                .andExpect(model().attributeExists("ingredient"));
    }

    @Test
    @WithMockUser
    void saveIngredient_ShouldRedirect_WhenDataIsValid() throws Exception {
        mockMvc.perform(post("/ingredients/save")
                .with(csrf())
                .param("name", "Tomato")) // Assuming name is a required field
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ingredients"));

        verify(ingredientService, times(1)).create(any(IngredientRequest.class));
    }

    @Test
    @WithMockUser
    void saveIngredient_ShouldReturnFormView_WhenDataIsInvalid() throws Exception {
        // Assuming validation fails if name is empty
        mockMvc.perform(post("/ingredients/save")
                .with(csrf())
                .param("name", "")
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("ingredients/form"));

        verify(ingredientService, never()).create(any(IngredientRequest.class));
    }

    @Test
    @WithMockUser
    void editIngredient_ShouldReturnFormView_WhenIdExists() throws Exception {
        Integer id = 1;
        IngredientResponse response = new IngredientResponse();
        response.setId(id);
        response.setName("Tomato");
        when(ingredientService.findById(id)).thenReturn(response);

        mockMvc.perform(get("/ingredients/edit/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("ingredients/form"))
                .andExpect(model().attributeExists("ingredient"));
    }

    @Test
    @WithMockUser
    void deleteIngredient_ShouldRedirect_WhenIdExists() throws Exception {
        Integer id = 1;

        mockMvc.perform(get("/ingredients/delete/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ingredients"));

        verify(ingredientService, times(1)).delete(id);
    }
}
