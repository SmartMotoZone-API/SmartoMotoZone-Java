package com.smartmotozone.api.smartmotozone_api.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para transferência de dados da entidade Movimentação.
 */
public record MovimentacaoDTO(

    Long id,

    @NotNull(message = "O ID da moto é obrigatório")
    Long motoId,

    @NotBlank(message = "A descrição da movimentação é obrigatória")
    String descricao,

    @NotNull(message = "A data e hora da movimentação são obrigatórias")
    LocalDateTime dataHora

) {}
