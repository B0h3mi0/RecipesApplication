package com.example.recetario_app.domain.entities.recipe;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "DETAILS_RECIPE")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DetailRecipeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "RECIPE_ID")
    private RecipeEntity recipe;

    @ManyToOne
    @JoinColumn(name = "INGREDIENT_ID")
    private IngredientEntity ingredient;

    @Column(name = "INGREDIENT_AMOUNT")
    private String ingredientAmount;

}
