package com.example.recetario_app.services.ingredient;

import com.example.recetario_app.domain.entities.recipe.IngredientEntity;
import com.example.recetario_app.domain.repositories.recipe.IngredientRepository;
import com.example.recetario_app.infrastructure.dtos.ingredient.IngredientRequest;
import com.example.recetario_app.infrastructure.dtos.ingredient.IngredientResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientServiceTest {

    @Mock
    private IngredientRepository repository;

    @InjectMocks
    private IngredientService ingredientService;

    @Test
    void findAll_ShouldReturnListOfIngredientResponses() {
        IngredientEntity entity1 = IngredientEntity.builder().id(1).name("Tomato").build();
        IngredientEntity entity2 = IngredientEntity.builder().id(2).name("Onion").build();
        when(repository.findAll()).thenReturn(Arrays.asList(entity1, entity2));

        List<IngredientResponse> result = ingredientService.findAll();

        assertEquals(2, result.size());
        assertEquals("Tomato", result.get(0).getName());
        assertEquals("Onion", result.get(1).getName());
        verify(repository, times(1)).findAll();
    }

    @Test
    void create_ShouldReturnIngredientResponse_WhenNameIsAvailable() {
        IngredientRequest request = new IngredientRequest();
        request.setName("Garlic");
        IngredientEntity savedEntity = IngredientEntity.builder().id(1).name("Garlic").build();

        when(repository.findByName("Garlic")).thenReturn(Optional.empty());
        when(repository.save(any(IngredientEntity.class))).thenReturn(savedEntity);

        IngredientResponse result = ingredientService.create(request);

        assertNotNull(result);
        assertEquals("Garlic", result.getName());
        verify(repository, times(1)).findByName("Garlic");
        verify(repository, times(1)).save(any(IngredientEntity.class));
    }

    @Test
    void create_ShouldThrowException_WhenNameAlreadyExists() {
        IngredientRequest request = new IngredientRequest();
        request.setName("Garlic");
        IngredientEntity existingEntity = IngredientEntity.builder().id(1).name("Garlic").build();

        when(repository.findByName("Garlic")).thenReturn(Optional.of(existingEntity));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> ingredientService.create(request));
        assertEquals("No es posible crear nuevo ingrediente, el nombre ya existe", exception.getMessage());
        verify(repository, times(1)).findByName("Garlic");
        verify(repository, never()).save(any(IngredientEntity.class));
    }

    @Test
    void update_ShouldReturnUpdatedResponse_WhenIdExistsAndNameIsNew() {
        Integer id = 1;
        IngredientRequest request = new IngredientRequest();
        request.setName("NewName");
        IngredientEntity existingEntity = IngredientEntity.builder().id(id).name("OldName").build();
        IngredientEntity updatedEntity = IngredientEntity.builder().id(id).name("NewName").build();

        when(repository.findById(id)).thenReturn(Optional.of(existingEntity));
        when(repository.findByName("NewName")).thenReturn(Optional.empty());
        when(repository.save(any(IngredientEntity.class))).thenReturn(updatedEntity);

        IngredientResponse result = ingredientService.update(id, request);

        assertEquals("NewName", result.getName());
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).findByName("NewName");
        verify(repository, times(1)).save(existingEntity);
    }

    @Test
    void update_ShouldThrowException_WhenIdDoesNotExist() {
        Integer id = 1;
        IngredientRequest request = new IngredientRequest();
        request.setName("NewName");

        when(repository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> ingredientService.update(id, request));
        assertEquals("No existe ingrediente con id: " + id, exception.getMessage());
        verify(repository, times(1)).findById(id);
        verify(repository, never()).save(any());
    }

    @Test
    void update_ShouldThrowException_WhenNameAlreadyExists() {
        Integer id = 1;
        IngredientRequest request = new IngredientRequest();
        request.setName("ExistingName");
        IngredientEntity existingEntity = IngredientEntity.builder().id(id).name("OldName").build();
        IngredientEntity conflictEntity = IngredientEntity.builder().id(2).name("ExistingName").build();

        when(repository.findById(id)).thenReturn(Optional.of(existingEntity));
        when(repository.findByName("ExistingName")).thenReturn(Optional.of(conflictEntity));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> ingredientService.update(id, request));
        assertEquals("No es posible crear nuevo ingrediente, el nombre ya existe", exception.getMessage());
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).findByName("ExistingName");
        verify(repository, never()).save(any());
    }

    @Test
    void update_ShouldUpdate_WhenNameIsSameAsExisting() {
        Integer id = 1;
        IngredientRequest request = new IngredientRequest();
        request.setName("OldName"); // Same name
        IngredientEntity existingEntity = IngredientEntity.builder().id(id).name("OldName").build();

        when(repository.findById(id)).thenReturn(Optional.of(existingEntity));
        when(repository.save(any(IngredientEntity.class))).thenReturn(existingEntity);

        IngredientResponse result = ingredientService.update(id, request);

        assertEquals("OldName", result.getName());
        verify(repository, times(1)).findById(id);
        verify(repository, never()).findByName(anyString()); // Should not check for existence if name is same
        verify(repository, times(1)).save(existingEntity);
    }

    @Test
    void delete_ShouldDelete_WhenIdExists() {
        Integer id = 1;
        when(repository.existsById(id)).thenReturn(true);

        ingredientService.delete(id);

        verify(repository, times(1)).existsById(id);
        verify(repository, times(1)).deleteById(id);
    }

    @Test
    void delete_ShouldThrowException_WhenIdDoesNotExist() {
        Integer id = 1;
        when(repository.existsById(id)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> ingredientService.delete(id));
        assertEquals("Ingrediente no existe", exception.getMessage());
        verify(repository, times(1)).existsById(id);
        verify(repository, never()).deleteById(id);
    }

    @Test
    void findById_ShouldReturnResponse_WhenIdExists() {
        Integer id = 1;
        IngredientEntity entity = IngredientEntity.builder().id(id).name("Salt").build();
        when(repository.findById(id)).thenReturn(Optional.of(entity));

        IngredientResponse result = ingredientService.findById(id);

        assertEquals("Salt", result.getName());
        verify(repository, times(1)).findById(id);
    }

    @Test
    void findById_ShouldThrowException_WhenIdDoesNotExist() {
        Integer id = 1;
        when(repository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> ingredientService.findById(id));
        assertEquals("No existe ingrediente con id: " + id, exception.getMessage());
        verify(repository, times(1)).findById(id);
    }
}
