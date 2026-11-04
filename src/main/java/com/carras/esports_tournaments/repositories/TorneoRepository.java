package com.carras.esports_tournaments.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.carras.esports_tournaments.model.Torneo;

public interface TorneoRepository extends JpaRepository<Torneo, Long> {
    
    @Query("SELECT t FROM Torneo t " +
            "LEFT JOIN FETCH t.inscripciones i " + 
            "LEFT JOIN FETCH i.equipo e " + 
            "WHERE t.id = :id")
    Optional<Torneo> findTorneoConDetalles(@Param("id") Long id);

    boolean existsByNombre(String nombre);
}
