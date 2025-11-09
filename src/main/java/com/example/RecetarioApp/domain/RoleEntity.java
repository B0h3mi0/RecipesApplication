package com.example.RecetarioApp.domain;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "ROLES")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ROLE_NAME")
    private String roleName;

    @Column(name = "DESCRIPTION")
    private String description;

}
