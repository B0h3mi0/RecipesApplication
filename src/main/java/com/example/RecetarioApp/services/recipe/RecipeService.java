package com.example.RecetarioApp.services.recipe;

import com.example.RecetarioApp.domain.entities.recipe.DetailRecipeEntity;
import com.example.RecetarioApp.domain.entities.recipe.IngredientEntity;
import com.example.RecetarioApp.domain.entities.recipe.RecipeEntity;
import com.example.RecetarioApp.domain.repositories.recipe.IngredientRepository;
import com.example.RecetarioApp.domain.repositories.recipe.RecipeRepository;
import com.example.RecetarioApp.infrastructure.dtos.recipe.RecipeRequest;
import com.example.RecetarioApp.infrastructure.dtos.recipe.RecipeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecipeService implements IRecipeService{
    private final RecipeRepository repository;
    private final IngredientRepository ingredientRepository;

    @Override
    public List<RecipeResponse> findAll() {
        log.info("Obtienendo lista de recetas");
        List<RecipeEntity> recipes = ( List<RecipeEntity> ) repository.findAll();

        log.info("Cantidad de recetas: {}",recipes.size());

        return recipes.stream()
                .map(RecipeResponse::fromEntity)
                .toList();
    }

    @Override
    public RecipeResponse findById(Long id) {
        log.info("Obteniendo receta por id: {}",id);
        RecipeEntity found = repository.findById(id).orElseThrow(()-> new RuntimeException("Receta no encontrada"));
        log.info("Receta encontrada");
        return RecipeResponse.fromEntity(found);
    }

    @Override
    public RecipeResponse create(RecipeRequest request) {
        log.info("Creando receta name: {}",request.getName());
        this.validName(request.getName());
        RecipeEntity toPersist = RecipeEntity.builder()
                .name(request.getName())
                .preparationTime(request.getPreparationTime())
                .description(request.getDescription())
                .createdAt(LocalDateTime.now())
                .instruction(request.getInstruction())
                .difficulty(request.getDifficulty())
                .country(request.getCountry())
                .type(request.getType())
                .build();
        this.addDetailEntitiesFromRequest(toPersist,request);
        RecipeEntity persisted = repository.save(toPersist);
        log.info("Receta Creada correctamente");
        return RecipeResponse.fromEntity(persisted);
    }

    @Override
    public RecipeResponse update(Long id, RecipeRequest request) {
        log.info("Actualizando receta id: {}",id);
        log.info("Buscando receta a actualizar");
        RecipeEntity toUpdate = repository.findById(id).orElseThrow(()->new RuntimeException("Receta no encontrada"));
        log.info("Receta con id: {} encontrada",id);
        if (!request.getName().equalsIgnoreCase(toUpdate.getName())){
            this.validName(request.getName());
            toUpdate.setName(request.getName());
        }
        toUpdate.setPreparationTime(request.getPreparationTime());
        toUpdate.setDescription(request.getDescription());
        toUpdate.setCountry(request.getCountry());
        toUpdate.setDifficulty(request.getDifficulty());
        toUpdate.setType(request.getType());
        toUpdate.setInstruction(request.getInstruction());
        toUpdate.getDetails().clear();
        this.addDetailEntitiesFromRequest(toUpdate,request);
        RecipeEntity updated = repository.save(toUpdate);
        log.info("Receta actualizada correctamente");
        return RecipeResponse.fromEntity(updated);
    }

    @Override
    public void delete(Long id) {
        log.info("Eliminando receta id: {}",id);
        if (!repository.existsById(id)){
            log.error("Receta con id: {} no existe",id);
            throw new RuntimeException("Receta no existe");
        }
        repository.deleteById(id);
        log.info("Receta eliminado correctamente");
    }

    private void addDetailEntitiesFromRequest(RecipeEntity entity, RecipeRequest request){
        log.info("Convirtiendo lista de RecipeRequest a lista de RecipeEntity");
        List<DetailRecipeEntity> detailsEntities = request.getDetails()
                .stream()
                .map(detail -> {
                            log.info("Validando existencia ingrediente id: {}",detail.getIngredientId());
                            IngredientEntity ingredient = ingredientRepository.findById(detail.getIngredientId())
                                    .orElseThrow(()->new RuntimeException("Ingrediente no existe"));
                            return DetailRecipeEntity.builder()
                                    .recipe(entity)
                                    .ingredient(ingredient)
                                    .ingredientAmount(detail.getAmount())
                                    .build();
                        }
                )
                .toList();
        entity.setDetails(detailsEntities);
    }


    private void validName(String name){
        log.info("Validando nombre receta: {}",name);
        Optional<RecipeEntity> existName = repository.findByName(name);
        if (existName.isPresent()){
            log.error("Nombre de Receta: {}, no disponible",name);
            throw new RuntimeException("No es posible crear nuevo receta, el nombre ya existe");
        }
    }


    @Override
    public List<RecipeResponse> findPopulars() {
        log.info("Obtienendo lista de recetas mas populares");
        List<RecipeEntity> recipes = ( List<RecipeEntity> ) repository.findAll();

        log.info("Cantidad de recetas: {}",recipes.size());

        return recipes.stream()
                .map(RecipeResponse::fromEntity)
                .toList();
    }

    @Override
    public List<RecipeResponse> findRecent() {
        log.info("Obtienendo lista de recetas creadas recientemente");
        List<RecipeEntity> recipes = ( List<RecipeEntity> ) repository.findAll();

        log.info("Cantidad de recetas: {}",recipes.size());

        return recipes.stream()
                .map(RecipeResponse::fromEntity)
                .toList();
    }
}
