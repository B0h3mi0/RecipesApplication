package com.example.recetario_app.services.recipe;

import com.example.recetario_app.domain.entities.recipe.DetailRecipeEntity;
import com.example.recetario_app.domain.entities.recipe.IngredientEntity;
import com.example.recetario_app.domain.entities.recipe.RecipeEntity;
import com.example.recetario_app.domain.repositories.recipe.IngredientRepository;
import com.example.recetario_app.domain.repositories.recipe.RecipeRepository;
import com.example.recetario_app.infrastructure.dtos.detail_recipe.DetailRecipeRequest;
import com.example.recetario_app.infrastructure.dtos.recipe.RecipeRequest;
import com.example.recetario_app.infrastructure.dtos.recipe.RecipeResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @Mock
    private RecipeRepository repository;

    @Mock
    private IngredientRepository ingredientRepository;

    @InjectMocks
    private RecipeService recipeService;

    @Test
    void findAll_ShouldReturnListOfRecipeResponses() {
        RecipeEntity entity1 = RecipeEntity.builder().id(1L).name("Pasta").details(new ArrayList<>()).build();
        RecipeEntity entity2 = RecipeEntity.builder().id(2L).name("Pizza").details(new ArrayList<>()).build();
        when(repository.findAll()).thenReturn(Arrays.asList(entity1, entity2));

        List<RecipeResponse> result = recipeService.findAll();

        assertEquals(2, result.size());
        assertEquals("Pasta", result.get(0).getName());
        assertEquals("Pizza", result.get(1).getName());
        verify(repository, times(1)).findAll();
    }

    @Test
    void findById_ShouldReturnRecipeResponse_WhenIdExists() {
        Long id = 1L;
        RecipeEntity entity = RecipeEntity.builder().id(id).name("Pasta").details(new ArrayList<>()).build();
        when(repository.findById(id)).thenReturn(Optional.of(entity));

        RecipeResponse result = recipeService.findById(id);

        assertEquals("Pasta", result.getName());
        verify(repository, times(1)).findById(id);
    }

    @Test
    void findById_ShouldThrowException_WhenIdDoesNotExist() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> recipeService.findById(id));
        assertEquals("Receta no encontrada", exception.getMessage());
        verify(repository, times(1)).findById(id);
    }

    @Test
    void create_ShouldReturnRecipeResponse_WhenDataIsValid() {
        RecipeRequest request = new RecipeRequest();
        request.setName("Pasta");
        request.setDetails(Collections.emptyList());

        RecipeEntity savedEntity = RecipeEntity.builder().id(1L).name("Pasta").details(new ArrayList<>()).build();

        when(repository.findByName("Pasta")).thenReturn(Optional.empty());
        when(repository.save(any(RecipeEntity.class))).thenReturn(savedEntity);

        RecipeResponse result = recipeService.create(request);

        assertNotNull(result);
        assertEquals("Pasta", result.getName());
        verify(repository, times(1)).findByName("Pasta");
        verify(repository, times(1)).save(any(RecipeEntity.class));
    }

    @Test
    void create_ShouldThrowException_WhenNameAlreadyExists() {
        RecipeRequest request = new RecipeRequest();
        request.setName("Pasta");
        RecipeEntity existingEntity = RecipeEntity.builder().id(1L).name("Pasta").details(new ArrayList<>()).build();

        when(repository.findByName("Pasta")).thenReturn(Optional.of(existingEntity));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> recipeService.create(request));
        assertEquals("No es posible crear nuevo receta, el nombre ya existe", exception.getMessage());
        verify(repository, times(1)).findByName("Pasta");
        verify(repository, never()).save(any(RecipeEntity.class));
    }

    @Test
    void create_ShouldThrowException_WhenIngredientDoesNotExist() {
        RecipeRequest request = new RecipeRequest();
        request.setName("Pasta");
        DetailRecipeRequest detailRequest = new DetailRecipeRequest();
        detailRequest.setIngredientId(1);
        request.setDetails(Collections.singletonList(detailRequest));

        when(repository.findByName("Pasta")).thenReturn(Optional.empty());
        when(ingredientRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> recipeService.create(request));
        assertEquals("Ingrediente no existe", exception.getMessage());
        verify(ingredientRepository, times(1)).findById(1);
        verify(repository, never()).save(any(RecipeEntity.class));
    }

    @Test
    void update_ShouldReturnUpdatedResponse_WhenIdExistsAndNameIsNew() {
        Long id = 1L;
        RecipeRequest request = new RecipeRequest();
        request.setName("NewName");
        request.setDetails(Collections.emptyList());

        RecipeEntity existingEntity = RecipeEntity.builder().id(id).name("OldName").details(new ArrayList<>()).build();
        RecipeEntity updatedEntity = RecipeEntity.builder().id(id).name("NewName").details(new ArrayList<>()).build();

        when(repository.findById(id)).thenReturn(Optional.of(existingEntity));
        when(repository.findByName("NewName")).thenReturn(Optional.empty());
        when(repository.save(any(RecipeEntity.class))).thenReturn(updatedEntity);

        RecipeResponse result = recipeService.update(id, request);

        assertEquals("NewName", result.getName());
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).findByName("NewName");
        verify(repository, times(1)).save(existingEntity);
    }

    @Test
    void update_ShouldThrowException_WhenIdDoesNotExist() {
        Long id = 1L;
        RecipeRequest request = new RecipeRequest();
        request.setName("NewName");

        when(repository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> recipeService.update(id, request));
        assertEquals("Receta no encontrada", exception.getMessage());
        verify(repository, times(1)).findById(id);
        verify(repository, never()).save(any());
    }

    @Test
    void update_ShouldThrowException_WhenNameAlreadyExists() {
        Long id = 1L;
        RecipeRequest request = new RecipeRequest();
        request.setName("ExistingName");
        RecipeEntity existingEntity = RecipeEntity.builder().id(id).name("OldName").details(new ArrayList<>()).build();
        RecipeEntity conflictEntity = RecipeEntity.builder().id(2L).name("ExistingName").details(new ArrayList<>())
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(existingEntity));
        when(repository.findByName("ExistingName")).thenReturn(Optional.of(conflictEntity));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> recipeService.update(id, request));
        assertEquals("No es posible crear nuevo receta, el nombre ya existe", exception.getMessage());
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).findByName("ExistingName");
        verify(repository, never()).save(any());
    }

    @Test
    void delete_ShouldDelete_WhenIdExists() {
        Long id = 1L;
        when(repository.existsById(id)).thenReturn(true);

        recipeService.delete(id);

        verify(repository, times(1)).existsById(id);
        verify(repository, times(1)).deleteById(id);
    }

    @Test
    void delete_ShouldThrowException_WhenIdDoesNotExist() {
        Long id = 1L;
        when(repository.existsById(id)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> recipeService.delete(id));
        assertEquals("Receta no existe", exception.getMessage());
        verify(repository, times(1)).existsById(id);
        verify(repository, never()).deleteById(id);
    }

    @Test
    void findPopulars_ShouldReturnListOfRecipeResponses() {
        RecipeEntity entity1 = RecipeEntity.builder().id(1L).name("Pasta").details(new ArrayList<>()).build();
        when(repository.findAll()).thenReturn(Collections.singletonList(entity1));

        List<RecipeResponse> result = recipeService.findPopulars();

        assertEquals(1, result.size());
        assertEquals("Pasta", result.get(0).getName());
        verify(repository, times(1)).findAll();
    }

    @Test
    void findRecent_ShouldReturnListOfRecipeResponses() {
        RecipeEntity entity1 = RecipeEntity.builder().id(1L).name("Pasta").details(new ArrayList<>()).build();
        when(repository.findAll()).thenReturn(Collections.singletonList(entity1));

        List<RecipeResponse> result = recipeService.findRecent();

        assertEquals(1, result.size());
        assertEquals("Pasta", result.get(0).getName());
        verify(repository, times(1)).findAll();
    }
}
