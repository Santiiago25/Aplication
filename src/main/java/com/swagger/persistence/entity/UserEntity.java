package com.swagger.persistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //que los usuarios no se puedan repetir
    @Column(unique = true)
    private String username;
    private String password;

    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "type_document")
    private String typeDocument;

    @Column(name = "num_document")
    private String numDocument;

    private String phone;

    private String address;

    private String sex;

//    private String departament;
//
//    private String city;

    //datos obligatorios para spring security
    @Column(name = "is_enable")
    private boolean isEnable;

    @Column(name = "account_no_expired")
    private boolean accountNoExpired;

    @Column(name = "account_no_locked")
    private boolean accountNoLocked;

    @Column(name = "credential_no_expired")
    private boolean credentialNoExpired;

    @Column(name = "estado")
    private String status;

    //El set es casi igual al list, solo que el set no permite repetidos
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    @JsonBackReference
    private CompanieEntity company;
}
