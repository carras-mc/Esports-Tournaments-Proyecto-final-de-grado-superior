package com.carras.esports_tournaments.dto;

import java.time.LocalDateTime;

import com.carras.esports_tournaments.model.enums.VisibilidadTorneo;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
public class TorneoRequestDto {
    
    @NotBlank(message = "El nombre del torneo es obligatorio")
    private String nombre;

    @NotNull(message = "El ID del creador es obligatorio")
    private Long creadorId;

    @NotBlank(message = "El juego del torneo es obligatorio")
    private String juego;

    @NotNull(message = "La fecha de inicio es obligatoria")
    @Future(message = "La fecha de inicio debe ser futura")
    private LocalDateTime fechaInicio;

    @NotNull(message = "La fecha de finalizacion es obligatoria")
    @Future(message = "La fecha inicial debe ser futura")
    private LocalDateTime fechaFinal;

    @NotNull(message = "Debe especificar el número máximo de jugadores")
    @Min(value = 1, message = "Debe haber al menos un jugador titular por cada equipo")
    private Integer maxJugadoresTitulares;

    @NotNull(message = "Debe especificar el número máximo de suplentes")
    @Min(value = 0, message = "El número de suplentes no puede ser negativo")
    private Integer maxSuplentes;

    @NotNull(message = "Debe de especificar si el torneo es publico o privado")
    private VisibilidadTorneo visibilidad;

    @NotNull(message = "Debe especificar la capacidad máxima de equipos")
    @Min(value = 2, message = "El torneo debe admitir al menos a 2 equipos para poder jugarse")
    private Integer capacidadMaxima;
}
