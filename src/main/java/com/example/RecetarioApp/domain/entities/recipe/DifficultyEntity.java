package com.example.RecetarioApp.domain.entities.recipe;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "DIFFICULTIES")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DifficultyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME")
    private String name;
}
