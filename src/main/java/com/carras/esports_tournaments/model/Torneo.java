package com.carras.esports_tournaments.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.carras.esports_tournaments.model.enums.EstadoTorneo;
import com.carras.esports_tournaments.model.enums.VisibilidadTorneo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "torneos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Torneo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Usuario creador;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private EstadoTorneo estado = EstadoTorneo.INSCRIPCION_ABIERTA;

    @OneToMany(mappedBy = "torneo")
    @Builder.Default
    private List<Inscripcion> inscripciones = new ArrayList<>();

    @Column(nullable = false)
    private String juego;

    @OneToMany(mappedBy = "torneo")
    @Builder.Default
    private List<Partida> partidas = new ArrayList<>();

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_final", nullable = false)
    private LocalDateTime fechaFinal;

    //MAX EQUIPOS
    @Column(name = "capacidad_maxima", nullable = false)
    private Integer capacidadMaxima;

    @Column(name = "jugadores_titulares", nullable = false)
    private Integer maxJugadoresTitulares;

    @Column(name = "max_suplentes", nullable = false)
    private Integer maxSuplentes;

    @Enumerated(EnumType.STRING)
    @Column(name = "visibilidad", nullable = false)
    @Builder.Default
    private VisibilidadTorneo visibilidadTorneo = VisibilidadTorneo.PUBLICO;

    
}
