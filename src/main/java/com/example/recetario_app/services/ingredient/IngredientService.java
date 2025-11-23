package com.example.recetario_app.services.ingredient;

import com.example.recetario_app.domain.entities.recipe.IngredientEntity;
import com.example.recetario_app.domain.repositories.recipe.IngredientRepository;
import com.example.recetario_app.infrastructure.dtos.ingredient.IngredientRequest;
import com.example.recetario_app.infrastructure.dtos.ingredient.IngredientResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class IngredientService implements IIngredientService{
    private final IngredientRepository repository;
    @Override
    public List<IngredientResponse> findAll() {
        log.info("Obteniendo todos los ingredientes");
        List<IngredientEntity> ingredients = (List<IngredientEntity>) repository.findAll();
        log.info("Cantidad de ingredientes: {}",ingredients.size());

        return ingredients.stream()
                .map(IngredientResponse::fromEntity)
                .toList();
    }

    @Override
    public IngredientResponse create(IngredientRequest request) {
        log.info("Creando ingrediente name: {}",request.getName());
        log.info("Validando nombre...");
        validExistName(request.getName());
        log.info("Nombre disponible");

        IngredientEntity toPersist = IngredientEntity.builder()
                .name(request.getName())
                .build();

        IngredientEntity persisted = repository.save(toPersist);

        log.info("Ingrediente creado exitosamente id: {}",persisted.getId());
        return IngredientResponse.fromEntity(persisted);
    }

    @Override
    public IngredientResponse update(Integer id, IngredientRequest request) {
        log.info("Actualizando ingrediente id: {}",id);
        log.info("Buscando ingrediente a actualizar...");
        IngredientEntity toUpdate = repository.findById(id).orElseThrow(()->new RuntimeException("No existe ingrediente con id: "+id));

        if (!request.getName().equalsIgnoreCase(toUpdate.getName())){
            log.info("Validando existencia de nombre de ingrediente");
            validExistName(request.getName());
            toUpdate.setName(request.getName());
        }

        IngredientEntity updated = repository.save(toUpdate);
        log.info("Ingrediente id: {}, actualizado correctamente",updated.getId());
        return IngredientResponse.fromEntity(updated);
    }

    @Override
    public void delete(Integer id) {
        log.info("Eliminando ingrediente id: {}",id);
        if (!repository.existsById(id)){
            log.error("Ingrediente con id: {} no existe",id);
            throw new RuntimeException("Ingrediente no existe");
        }
        repository.deleteById(id);
        log.info("Ingrediente eliminado correctamente");
    }


    private void validExistName(String name){
        log.info("Validando nombre ingrediente: {}",name);
        Optional<IngredientEntity> existName = repository.findByName(name);
        if (existName.isPresent()){
            log.error("Nombre de ingrediente: {}, no disponible",name);
            throw new RuntimeException("No es posible crear nuevo ingrediente, el nombre ya existe");
        }
    }

    @Override
    public IngredientResponse findById(Integer id) {
        log.info("Obteniendo ingrediente por id: {}",id);
        IngredientEntity found = repository.findById(id).orElseThrow(()->new RuntimeException("No existe ingrediente con id: "+id));
        log.info("Ingriente encontrado");
        return IngredientResponse.fromEntity(found);
    }
}
