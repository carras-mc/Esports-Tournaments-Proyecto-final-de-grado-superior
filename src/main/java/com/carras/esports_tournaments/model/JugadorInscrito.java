package com.carras.esports_tournaments.model;

import com.carras.esports_tournaments.model.enums.EstadoSolicitudInscripcion;
import com.carras.esports_tournaments.model.enums.RolJugador;
import com.carras.esports_tournaments.model.enums.RolMiembro;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "inscripciones_jugadores")
@Getter 
@Setter 
@AllArgsConstructor 
@NoArgsConstructor 
@Builder
public class JugadorInscrito {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "inscripcion_id", nullable = false)
    private Inscripcion inscripcion;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RolJugador rol;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private EstadoSolicitudInscripcion estadoSolicitudInscripcion = EstadoSolicitudInscripcion.PENDIENTE_CONFIRMACION;
}
