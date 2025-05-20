package com.smartmotozone.api.smartmotozone_api.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MovimentacaoDTO(
    Long id,
    @NotNull Long motoId,
    @NotBlank String descricao,
    @NotNull LocalDateTime dataHora
) {}