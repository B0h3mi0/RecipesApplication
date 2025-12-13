package com.example.recetario_app.infrastructure.controllers;

import com.example.recetario_app.infrastructure.dtos.recipe.RecipeRequest;
import com.example.recetario_app.infrastructure.dtos.recipe.RecipeResponse;
import com.example.recetario_app.services.ingredient.IIngredientService;
import com.example.recetario_app.services.recipe.IRecipeService;
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

@WebMvcTest(RecipeController.class)
class RecipeControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private IIngredientService ingredientService;

        @MockBean
        private IRecipeService recipeService;

        @Test
        @WithMockUser
        void listRecipes_ShouldReturnListView() throws Exception {
                when(recipeService.findAll()).thenReturn(Collections.emptyList());

                mockMvc.perform(get("/recipes"))
                                .andExpect(status().isOk())
                                .andExpect(view().name("recipes/list"))
                                .andExpect(model().attributeExists("recipes"));
        }

        @Test
        @WithMockUser
        void createRecipe_ShouldReturnFormView() throws Exception {
                when(ingredientService.findAll()).thenReturn(Collections.emptyList());

                mockMvc.perform(get("/recipes/create"))
                                .andExpect(status().isOk())
                                .andExpect(view().name("recipes/form"))
                                .andExpect(model().attributeExists("recipe"))
                                .andExpect(model().attributeExists("ingredients"))
                                .andExpect(model().attributeExists("countries"))
                                .andExpect(model().attributeExists("recipeTypes"));
        }

        @Test
        @WithMockUser
        void saveRecipe_ShouldRedirect_WhenDataIsValid() throws Exception {
                mockMvc.perform(post("/recipes/save")
                                .with(csrf())
                                .param("name", "Pasta Carbonara")
                                .param("description", "Delicious pasta carbonara with eggs and cheese")
                                .param("preparationTime", "30 minutes")
                                .param("difficulty", "Medium")
                                .param("country", "Italy")
                                .param("type", "Main Course")
                                .param("instruction", "Cook pasta, mix eggs and cheese, combine."))
                                .andDo(result -> {
                                        if (result.getModelAndView() != null && result.getModelAndView().getModel()
                                                        .containsKey("org.springframework.validation.BindingResult.recipeRequest")) {
                                                System.out.println(result.getModelAndView().getModel().get(
                                                                "org.springframework.validation.BindingResult.recipeRequest"));
                                        }
                                })
                                .andExpect(status().is3xxRedirection())
                                .andExpect(redirectedUrl("/recipes"));

                verify(recipeService, times(1)).create(any(RecipeRequest.class));
        }

        @Test
        @WithMockUser
        void saveRecipe_ShouldReturnFormView_WhenDataIsInvalid() throws Exception {
                mockMvc.perform(post("/recipes/save")
                                .with(csrf())
                                .param("name", "")) // Invalid name
                                .andExpect(status().isOk())
                                .andExpect(view().name("recipes/form"))
                                .andExpect(model().attributeExists("ingredients"));

                verify(recipeService, never()).create(any(RecipeRequest.class));
        }

        @Test
        @WithMockUser
        void viewRecipe_ShouldReturnViewView_WhenIdExists() throws Exception {
                Long id = 1L;
                RecipeResponse response = RecipeResponse.builder()
                                .id(id)
                                .name("Pasta")
                                .build();
                when(recipeService.findById(id)).thenReturn(response);

                mockMvc.perform(get("/recipes/{id}", id))
                                .andExpect(status().isOk())
                                .andExpect(view().name("recipes/view"))
                                .andExpect(model().attributeExists("recipe"));
        }

        @Test
        @WithMockUser
        void editRecipe_ShouldReturnFormView_WhenIdExists() throws Exception {
                Long id = 1L;
                RecipeResponse response = RecipeResponse.builder()
                                .id(id)
                                .name("Pasta")
                                .build();
                when(recipeService.findById(id)).thenReturn(response);
                when(ingredientService.findAll()).thenReturn(Collections.emptyList());

                mockMvc.perform(get("/recipes/edit/{id}", id))
                                .andExpect(status().isOk())
                                .andExpect(view().name("recipes/form"))
                                .andExpect(model().attributeExists("recipe"))
                                .andExpect(model().attributeExists("ingredients"))
                                .andExpect(model().attributeExists("countries"))
                                .andExpect(model().attributeExists("recipeTypes"));
        }

        @Test
        @WithMockUser
        void deleteRecipe_ShouldRedirect_WhenIdExists() throws Exception {
                Long id = 1L;

                mockMvc.perform(post("/recipes/delete/{id}", id)
                                .with(csrf()))
                                .andExpect(status().is3xxRedirection())
                                .andExpect(redirectedUrl("/recipes"));

                verify(recipeService, times(1)).delete(id);
        }
}
