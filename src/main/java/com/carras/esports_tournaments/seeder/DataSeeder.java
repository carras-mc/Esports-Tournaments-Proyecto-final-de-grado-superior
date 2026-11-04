package com.carras.esports_tournaments.seeder;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.carras.esports_tournaments.model.Equipo;
import com.carras.esports_tournaments.model.Torneo;
import com.carras.esports_tournaments.model.Usuario;
import com.carras.esports_tournaments.model.enums.EstadoTorneo;
import com.carras.esports_tournaments.model.enums.Rol;
import com.carras.esports_tournaments.model.enums.VisibilidadTorneo;
import com.carras.esports_tournaments.repositories.EquipoRepository;
import com.carras.esports_tournaments.repositories.TorneoRepository;
import com.carras.esports_tournaments.repositories.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final TorneoRepository torneoRepository;
    private final EquipoRepository equipoRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) throws Exception {
        
        if (torneoRepository.count() == 0) {

           
            
            Equipo equipo = Equipo.builder()
                .nombre("We are the seed")
                .fechaCreacion(LocalDate.now())
                .build();
            
            equipoRepository.save(equipo);

            Usuario usuario = Usuario.builder()
                .nombre("Marcos")
                .primerApellido("Muñoz")
                .segundoApellido("Carrasco")
                .fechaNacimiento(LocalDate.of(2006, 7, 5))
                .email("marcosmunozcarrasco06@gmail.com")
                .telefono("640889571")
                .password("markosSeeder97")
                .username("Markiwi")
                .rol(Rol.USER)
                .build();

            usuarioRepository.save(usuario);

            Torneo torneo = Torneo.builder()
                .nombre("El Seeder Tournament")
                .estado(EstadoTorneo.INSCRIPCION_ABIERTA)
                .capacidadMaxima(6)
                .juego("CSGO 2")
                .fechaInicio(LocalDateTime.of(2026, 10, 20, 20, 0))
                .fechaFinal(LocalDateTime.of(2026, 10, 25, 22, 30))
                .maxJugadoresTitulares(5)
                .maxSuplentes(2)
                .visibilidadTorneo(VisibilidadTorneo.PUBLICO)
                .creador(usuario)
                .build();

            torneoRepository.save(torneo);
                    
            System.out.println("¡Datos de prueba inicializados con éxito!");
        }
    }
}
