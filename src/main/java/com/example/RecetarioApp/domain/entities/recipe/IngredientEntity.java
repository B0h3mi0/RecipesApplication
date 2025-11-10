package com.example.RecetarioApp.domain.entities.recipe;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "INGREDIENTS")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class IngredientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME",unique = true)
    private String name;
}
