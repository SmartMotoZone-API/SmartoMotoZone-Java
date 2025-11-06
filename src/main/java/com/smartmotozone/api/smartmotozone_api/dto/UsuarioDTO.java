package com.smartmotozone.api.smartmotozone_api.dto;

import jakarta.validation.constraints.NotBlank;

public record UsuarioDTO(

    Long id,

    @NotBlank(message = "O nome do usuário é obrigatório")
    String nome,

    @NotBlank(message = "O perfil do usuário é obrigatório")
    String perfil,

    @NotBlank(message = "O login do usuário é obrigatório")
    String login,

    @NotBlank(message = "A senha do usuário é obrigatória")
    String senha,

    @NotBlank(message = "O email do usuário é obrigatória")
    String email

) {}
