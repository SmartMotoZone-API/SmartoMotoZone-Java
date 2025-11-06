package com.smartmotozone.api.smartmotozone_api.dto.security;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
        @NotBlank String login,
        @NotBlank String senha
) {}