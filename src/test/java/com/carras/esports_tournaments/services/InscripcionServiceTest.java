package com.carras.esports_tournaments.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.carras.esports_tournaments.dto.InscripcionResponseDto;
import com.carras.esports_tournaments.exceptions.BusinessException;
import com.carras.esports_tournaments.model.Equipo;
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

@ExtendWith(MockitoExtension.class)
public class InscripcionServiceTest {

    @Mock
    private InscripcionRepository inscripcionRepository;

    @Mock
    private TorneoRepository torneoRepository;

    @Mock
    private EquipoRepository equipoRepository;

    @InjectMocks
    private InscripcionService inscripcionService;

    @Mock
    private MiembroEquipoRepository miembroEquipoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    void debeLanzarExcepcionCuandoTorneoEstaLleno() {
        Torneo torneoPrueba = Torneo.builder()
                .id(1L)
                .nombre("Torneo de prueba")
                .juego("Counter Strike")
                .estado(EstadoTorneo.INSCRIPCION_ABIERTA)
                .capacidadMaxima(8)
                .fechaInicio(LocalDateTime.now())
                .fechaFinal(LocalDateTime.now().plusDays(1))
                .build();

        Equipo equipoPrueba = Equipo.builder()
                .id(1L)
                .nombre("Equipo de prueba")
                .activo(true)
                .fechaCreacion(LocalDate.now())
                .build();

        when(torneoRepository.findById(1L)).thenReturn(Optional.of(torneoPrueba));
        when(equipoRepository.findById(1L)).thenReturn(Optional.of(equipoPrueba));
        when(inscripcionRepository.countByTorneo(torneoPrueba)).thenReturn(8L);

        BusinessException excepcion = assertThrows(BusinessException.class, () -> {
            inscripcionService.inscribirEquipo(1L, 1L, 1L);
        });

        assertEquals("Capacidad máxima de integrantes del torneo superada.", excepcion.getMessage());

        verify(inscripcionRepository, never()).save(any());
    }

    @Test
    void debeInscribirExitosamente() {

        Torneo torneoPrueba = Torneo.builder()
                .id(1L).nombre("Torneo de prueba").juego("Counter Strike")
                .estado(EstadoTorneo.INSCRIPCION_ABIERTA).capacidadMaxima(8)
                .fechaInicio(LocalDateTime.now()).fechaFinal(LocalDateTime.now().plusDays(1))
                .build();

        Equipo equipoPrueba = Equipo.builder()
                .id(1L).nombre("Equipo de prueba").activo(true).fechaCreacion(LocalDate.now())
                .build();

        Usuario usuarioPrueba = Usuario.builder()
                .id(1L).email("marcos@gmail.com").nombre("Marcos").username("markiwi").build();

        MiembroEquipo miembroPrueba = MiembroEquipo.builder()
                .usuario(usuarioPrueba).equipo(equipoPrueba).rol(RolMiembro.CREADOR)
                .build();

        when(torneoRepository.findById(1L)).thenReturn(Optional.of(torneoPrueba));
        when(equipoRepository.findById(1L)).thenReturn(Optional.of(equipoPrueba));
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioPrueba));
        when(miembroEquipoRepository.findByUsuarioAndEquipo(usuarioPrueba, equipoPrueba)).thenReturn(Optional.of(miembroPrueba));

        when(inscripcionRepository.existsByTorneoAndEquipo(torneoPrueba, equipoPrueba)).thenReturn(false);
        when(inscripcionRepository.countByTorneo(torneoPrueba)).thenReturn(3L);
        when(inscripcionRepository.save(any(Inscripcion.class))).thenAnswer(invocation -> invocation.getArgument(0));

        InscripcionResponseDto resultado = inscripcionService.inscribirEquipo(1L, 1L, 1L);

        assertNotNull(resultado);
        assertEquals(EstadoInscripcion.PENDIENTE, resultado.getEstadoInscripcion());
        assertEquals(equipoPrueba.getNombre(), resultado.getNombreEquipo());
        assertEquals(torneoPrueba.getNombre(), resultado.getNombreTorneo());
        assertEquals(usuarioPrueba.getUsername(), resultado.getNombreUsuarioAccion());

        verify(inscripcionRepository, times(1)).save(any(Inscripcion.class));

    }
}
