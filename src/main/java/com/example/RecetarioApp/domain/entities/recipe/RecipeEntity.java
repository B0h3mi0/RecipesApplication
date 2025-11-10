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

    @Column(name = "DIFFICULTY",nullable = false, length = 10)
    private String difficulty;

    @Column(name = "COUNTRY",nullable = false, length = 30)
    private String country;

    @Column(name = "TYPE",nullable = false, length = 30)
    private String type;

    @Column(name = "INSTRUCTION",nullable = false)
    private String instruction;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

}
