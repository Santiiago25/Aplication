package com.swagger.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "companie")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CompanieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true) //que no se pueda repetir el nit
    private int nit;

    @Column(unique = true) //que las empresas no se repitan
    private String company;

    private String manager;

    private String email;

    private String phone;

    private String address;

    private String department;

    private String municipality;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<UserEntity> users = new HashSet<>();
}
