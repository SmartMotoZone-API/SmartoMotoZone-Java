// ZonaDTO.java
package com.smartmotozone.api.smartmotozone_api.dto;

import jakarta.validation.constraints.NotBlank;

public record ZonaDTO(
    Long id,
    @NotBlank String codigo,
    @NotBlank String descricao
) {}
