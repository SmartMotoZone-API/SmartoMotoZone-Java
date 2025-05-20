// Moto.java
package com.smartmotozone.api.smartmotozone_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Moto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String modelo;

    @NotBlank
    private String placa;

    @NotBlank
    private String status;

    @ManyToOne
    @JoinColumn(name = "zona_id")
    private Zona zona;
}