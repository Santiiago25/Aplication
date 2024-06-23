package com.swagger.presentation.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"username", "message", "jwt", "status", "estado"})
public record AuthResponse(String username,
                           String message,
                           String jwt,
                           boolean status,
                           String estado) {
}
