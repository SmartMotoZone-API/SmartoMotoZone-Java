package com.smartmotozone.api.smartmotozone_api.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO para transferência de dados da entidade Zona.
 */
public record ZonaDTO(

    Long id,

    @NotBlank(message = "O código da zona é obrigatório")
    String codigo,

    @NotBlank(message = "A descrição da zona é obrigatória")
    String descricao

) {}
