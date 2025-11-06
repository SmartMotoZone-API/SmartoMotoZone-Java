// Usuario.java
package com.smartmotozone.api.smartmotozone_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Data
@Table(name = "USUARIO")
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String login;

    @NotBlank
    private String nome;

    @NotBlank
    private String perfil;

    @NotBlank
    private String senha;

    @NotBlank
    private String email;
}