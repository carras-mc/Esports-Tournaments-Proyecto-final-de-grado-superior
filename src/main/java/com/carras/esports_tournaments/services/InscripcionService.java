package com.carras.esports_tournaments.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.carras.esports_tournaments.model.Inscripcion;
import com.carras.esports_tournaments.model.MiembroEquipo;
import com.carras.esports_tournaments.model.Torneo;
import com.carras.esports_tournaments.model.Usuario;
import com.carras.esports_tournaments.model.enums.EstadoInscripcion;
import com.carras.esports_tournaments.model.enums.EstadoTorneo;
import com.carras.esports_tournaments.model.enums.RolMiembro;
import com.carras.esports_tournaments.repositories.EquipoRepository;
import com.carras.esports_tournaments.repositories.InscripcionRepository;
import com.carras.esports_tournaments.repositories.MiembroEquipoRepository;
import com.carras.esports_tournaments.repositories.TorneoRepository;
import com.carras.esports_tournaments.repositories.UsuarioRepository;

import jakarta.transaction.Transactional;

import com.carras.esports_tournaments.dto.InscripcionResponseDto;
import com.carras.esports_tournaments.exceptions.BusinessException;
import com.carras.esports_tournaments.model.Equipo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InscripcionService {

    private final InscripcionRepository inscripcionRepository;
    private final TorneoRepository torneoRepository;
    private final EquipoRepository equipoRepository;
    private final UsuarioRepository usuarioRepository;
    private final MiembroEquipoRepository miembroEquipoRepository;

    @Transactional
    public InscripcionResponseDto inscribirEquipo(Long torneoId, Long equipoId, Long usuarioAccionId) {
        Torneo torneo = torneoRepository.findById(torneoId)
                .orElseThrow(() -> new BusinessException("Torneo no encontrado."));
        Equipo equipo = equipoRepository.findById(equipoId)
                .orElseThrow(() -> new BusinessException("Equipo no encontrado."));
        Usuario usuario = usuarioRepository.findById(usuarioAccionId)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado."));
        MiembroEquipo miembroEquipo = miembroEquipoRepository.findByUsuarioAndEquipo(usuario, equipo)
                .orElseThrow(() -> new BusinessException("El usuario no se encuentra en el equipo."));


        if (miembroEquipo.getRol() != RolMiembro.ADMINISTRADOR && miembroEquipo.getRol() != RolMiembro.CREADOR) {
            throw new BusinessException("El usuario no tiene permisos suficientes.");
        }
        if (!torneo.getEstado().equals(EstadoTorneo.INSCRIPCION_ABIERTA)) {
            throw new BusinessException("El torneo no acepta inscripciones.");
        }
        if (inscripcionRepository.countByTorneo(torneo) >= torneo.getCapacidadMaxima()) {
            throw new BusinessException("Capacidad máxima de integrantes del torneo superada.");
        }
        if (inscripcionRepository.existsByTorneoAndEquipo(torneo, equipo)) {
            throw new BusinessException("Ya existe una inscripción para el torneo y el equipo.");
        }

        Inscripcion inscripcion = Inscripcion.builder()
                .torneo(torneo)
                .equipo(equipo)
                .usuarioAccion(usuario)
                .build();

        inscripcion = inscripcionRepository.save(inscripcion);

        List<String> nombresRoster = inscripcion.getRoster().stream()
            .map(jugadorInscrito -> jugadorInscrito.getUsuario().getUsername())
            .toList();

        InscripcionResponseDto inscripcionResponseDto = InscripcionResponseDto.builder()
            .id(inscripcion.getId())
            .nombreTorneo(inscripcion.getTorneo().getNombre())
            .nombreEquipo(inscripcion.getEquipo().getNombre())
            .nombreUsuarioAccion(inscripcion.getUsuarioAccion().getNombre())
            .roster(nombresRoster)
            .estadoInscripcion(inscripcion.getEstado())
            .fecha(inscripcion.getFechaInscripcion())
            .build();

        return inscripcionResponseDto;
    }
}