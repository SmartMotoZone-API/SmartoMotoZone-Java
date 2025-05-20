package com.smartmotozone.api.smartmotozone_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para transferência de dados da entidade Moto.
 */
public record MotoDTO(

    Long id,

    @NotBlank(message = "O modelo da moto é obrigatório")
    String modelo,

    @NotBlank(message = "A placa da moto é obrigatória")
    String placa,

    @NotNull(message = "O ID da zona é obrigatório")
    Long zonaId,

    @NotBlank(message = "O status da moto é obrigatório")
    String status

) {}
