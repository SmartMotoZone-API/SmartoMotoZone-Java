package com.smartmotozone.api.smartmotozone_api.dto;

import jakarta.validation.constraints.NotBlank;


public record FuncionarioDTO(
    Long id,
    @NotBlank String nome,
    @NotBlank String cargo
) {}
