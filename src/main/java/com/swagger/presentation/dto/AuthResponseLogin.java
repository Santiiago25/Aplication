package com.swagger.presentation.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"username", "message", "jwt", "status", "estado"})
public record AuthResponseLogin(String username,
                           String message,
                           String jwt,
                           boolean status,
                                String estado) {
}


