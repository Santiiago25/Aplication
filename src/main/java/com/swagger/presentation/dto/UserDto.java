package com.swagger.presentation.dto;

import com.swagger.persistence.entity.RoleEntity;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserDto implements Serializable {
    private Long id;
    private String username;
    private String name;
    private String lastName;
    private String typeDocument;
    private String numDocument;
    private String phone;
    private String address;
    private boolean isEnable;
    private boolean accountNoExpired;
    private boolean accountNoLocked;
    private boolean credentialNoExpired;
    private String status;
    private Set<RoleEntity> roles;
    private CompanieDto company;
}
