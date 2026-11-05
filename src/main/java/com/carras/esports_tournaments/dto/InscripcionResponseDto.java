package com.carras.esports_tournaments.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.carras.esports_tournaments.model.JugadorInscrito;
import com.carras.esports_tournaments.model.enums.EstadoInscripcion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@AllArgsConstructor 
@NoArgsConstructor 
@Builder 
public class InscripcionResponseDto {
    
    private Long id;
    private String nombreTorneo;
    private String nombreEquipo;
    private String nombreUsuarioAccion;
    private List<String> roster;
    private EstadoInscripcion estadoInscripcion;
    private LocalDateTime fecha;

}
