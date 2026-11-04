package com.carras.esports_tournaments.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.carras.esports_tournaments.dto.TorneoRequestDto;
import com.carras.esports_tournaments.dto.TorneoResponseDto;
import com.carras.esports_tournaments.exceptions.BusinessException;
import com.carras.esports_tournaments.model.Torneo;
import com.carras.esports_tournaments.model.Usuario;
import com.carras.esports_tournaments.repositories.TorneoRepository;
import com.carras.esports_tournaments.repositories.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TorneoService {

    private final TorneoRepository torneoRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public TorneoResponseDto crearTorneo(TorneoRequestDto request) {
        if (torneoRepository.existsByNombre(request.getNombre()))
            throw new BusinessException("Ya existe un torneo con ese nombre");

        Usuario creador = usuarioRepository.findById(request.getCreadorId()).orElseThrow(() -> new BusinessException("El usuario creador del torneo no existe"));

        Torneo torneo = Torneo.builder()
            .nombre(request.getNombre())
            .creador(creador)
            .juego(request.getJuego())
            .fechaInicio(request.getFechaInicio())
            .fechaFinal(request.getFechaFinal())
            .capacidadMaxima(request.getCapacidadMaxima())
            .maxJugadoresTitulares(request.getMaxJugadoresTitulares())
            .maxSuplentes(request.getMaxSuplentes())
            .visibilidadTorneo(request.getVisibilidad())
            .build();

        torneo = torneoRepository.save(torneo);

        TorneoResponseDto torneoResponseDto = TorneoResponseDto.builder()
            .id(torneo.getId())
            .nombre(torneo.getNombre())
            .creadorNombre(torneo.getCreador().getUsername())
            .creadorId(torneo.getCreador().getId())
            .juego(torneo.getJuego())
            .estado(torneo.getEstado())
            .fechaInicio(torneo.getFechaInicio())
            .fechaFinal(torneo.getFechaFinal())
            .capacidadMaxima(torneo.getCapacidadMaxima())
            .maxJugadoresTitulares(torneo.getMaxJugadoresTitulares())
            .maxSuplentes(torneo.getMaxSuplentes())
            .visibilidad(torneo.getVisibilidadTorneo())
            .build();

        return torneoResponseDto;
            
    }

    //TODO: AJUSTAR A LOS NUEVOS ATRIBUTOS DE TORNEO 
    @Transactional(readOnly = true)
    public TorneoResponseDto obtenerTorneoConDetalles(Long id) {
        // return torneoRepository.findTorneoConDetalles(id).orElseThrow(() -> new
        // BusinessException("Torneo no encontrado"));
        Torneo torneo = torneoRepository.findTorneoConDetalles(id)
                .orElseThrow(() -> new BusinessException("Torneo no encontrado"));

        List<String> nombresEquipos = torneo.getInscripciones().stream()
                .map(inscripcion -> inscripcion.getEquipo().getNombre())
                .toList();

        TorneoResponseDto torneoDto = TorneoResponseDto.builder()
                .id(id)
                .nombre(torneo.getNombre())
                .juego(torneo.getJuego())
                .estado(torneo.getEstado())
                .fechaInicio(torneo.getFechaInicio())
                .capacidadMaxima(torneo.getCapacidadMaxima())
                .build();

        return torneoDto;

    }
}
