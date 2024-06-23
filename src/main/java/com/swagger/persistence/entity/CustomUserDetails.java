package com.swagger.persistence.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomUserDetails {

    private String status;

    public CustomUserDetails( String status) {
        this.status = status;
    }

}
