package com.smartmotozone.api.smartmotozone_api.dto;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para transferência de dados da entidade Movimentação.
 */
public record MovimentacaoDTO(
    Long id,

    @NotNull(message = "O ID da moto é obrigatório")
    Long motoId,

    @NotNull(message = "O ID da zona de origem é obrigatório")
    Long zonaOrigemId,

    @NotNull(message = "O ID da zona de destino é obrigatório")
    Long zonaDestinoId,

    @NotNull(message = "A descrição da movimentação é obrigatória")
    String descricao,

    @NotNull(message = "A data e hora da movimentação são obrigatórias")
    LocalDateTime dataHora
) {}
