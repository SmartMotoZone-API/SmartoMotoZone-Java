package com.smartmotozone.api.smartmotozone_api.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO para transferência de dados da entidade Funcionario.
 */
public record FuncionarioDTO(

    Long id,

    @NotBlank(message = "O nome do funcionário é obrigatório")
    String nome,

    @NotBlank(message = "O cargo do funcionário é obrigatório")
    String cargo

) {}
