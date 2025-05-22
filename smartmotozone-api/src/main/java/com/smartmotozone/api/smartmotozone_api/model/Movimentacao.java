package com.smartmotozone.api.smartmotozone_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "MOVIMENTACAO")
@NoArgsConstructor
@AllArgsConstructor
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Moto moto;

    @ManyToOne
    private Zona zonaOrigem;

    @ManyToOne
    private Zona zonaDestino;

    private String descricao;

    private LocalDateTime dataHora;
}
