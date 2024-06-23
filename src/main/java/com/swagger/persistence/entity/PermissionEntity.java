package com.swagger.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "permissions")
public class PermissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Debe de ser unico, no es nulo y no se puede actualizar
    @Column(unique = true, nullable = false, updatable = false) //READ_PRODUCTS
    private String name;

}
