package com.smartmotozone.api.smartmotozone_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Data
@Table(name = "MOTO")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Moto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O modelo da moto é obrigatório")
    private String modelo;

    @NotBlank(message = "A placa da moto é obrigatória")
    private String placa;

    @NotBlank(message = "O status da moto é obrigatório")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zona_id", nullable = false)
    @NotNull(message = "A zona da moto é obrigatória")
    private Zona zona;
}
