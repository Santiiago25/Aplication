package com.swagger.presentation.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public record AuthCreateUserRequest(@NotBlank String username,
                                    @NotBlank String password,
                                    @Valid AuthCreateRoleRequest roleRequest,
                                    @NotBlank String estado,
                                    @NotNull Long companyId,
                                    @NotBlank String name,
                                    @NotBlank String lastName,
                                    @NotBlank String typeDocument,
                                    @NotBlank String numDocument,
                                    @NotBlank String phone,
                                    @NotBlank String address) {
}


