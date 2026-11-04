package com.carras.esports_tournaments.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.carras.esports_tournaments.model.enums.EstadoInscripcion;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "inscripciones")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "torneo_id", nullable = false)
    private Torneo torneo;

    @ManyToOne
    @JoinColumn(name = "equipo_id", nullable = false)
    private Equipo equipo;

    @ManyToOne 
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuarioAccion;

    @OneToMany(mappedBy = "inscripcion", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<JugadorInscrito> roster = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private EstadoInscripcion estado = EstadoInscripcion.PENDIENTE;

    @Column(name = "fecha_inscripcion", nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime fechaInscripcion = LocalDateTime.now();

    public void addJugadorAlRoster(JugadorInscrito jugadorInscrito) {
        roster.add(jugadorInscrito);
    }
  
}
