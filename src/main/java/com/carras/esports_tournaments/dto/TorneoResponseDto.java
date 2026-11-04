package com.carras.esports_tournaments.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.carras.esports_tournaments.model.enums.EstadoTorneo;
import com.carras.esports_tournaments.model.enums.VisibilidadTorneo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TorneoResponseDto {
    
    private Long id;
    private String nombre;
    private String creadorNombre;
    private Long creadorId;
    private String juego;
    private EstadoTorneo estado;
    private Integer capacidadMaxima;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFinal;
    private Integer maxJugadoresTitulares;
    private Integer maxSuplentes;
    private List<String> equipos;
    private VisibilidadTorneo visibilidad;

}
