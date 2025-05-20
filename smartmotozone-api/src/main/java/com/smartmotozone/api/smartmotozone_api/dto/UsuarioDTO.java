package com.smartmotozone.api.smartmotozone_api.dto;

import jakarta.validation.constraints.NotBlank;

public record UsuarioDTO(
    Long id,
    @NotBlank String login,
    @NotBlank String senha
) {}