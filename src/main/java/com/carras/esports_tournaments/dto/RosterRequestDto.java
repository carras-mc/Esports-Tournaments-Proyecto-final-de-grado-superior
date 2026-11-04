package com.carras.esports_tournaments.dto;

import java.util.List;

import com.carras.esports_tournaments.model.enums.RolJugador;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
public class RosterRequestDto {
    
    @NotNull(message = "El ID del usuario es obligatorio")
    private Long usuarioAccionId;

    @Valid
    @NotNull(message = "La lista de juagadores es obligatoria")
    private List<JugadorRosterDto> jugadores;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class JugadorRosterDto {

        @NotNull(message = "El ID del usuario es obligatorio")
        private Long usuarioId;

        @NotNull(message = "El rol del jugador es obligatorio")
        private RolJugador rol;
    }
}

