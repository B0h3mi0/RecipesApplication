package com.example.RecetarioApp.domain.entities.recipe;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "RECIPES")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RecipeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME",nullable = false, unique = true,length = 60)
    private String name;

    @Column(name = "PREPARATION_TIME", nullable = false, length = 50)
    private String preparationTime;

    @Column(name = "DESCRIPTION",nullable = false, length = 200)
    private String description;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetailRecipeEntity> details;

    @ManyToOne
    @JoinColumn(name = "DIFFICULTY_ID")
    private DifficultyEntity difficulty;

    @ManyToOne
    @JoinColumn(name = "COUNTRY_ID")
    private CountryEntity country;

    @ManyToOne
    @JoinColumn(name = "RECIPE_TYPE_ID")
    private RecipeTypeEntity type;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

}
