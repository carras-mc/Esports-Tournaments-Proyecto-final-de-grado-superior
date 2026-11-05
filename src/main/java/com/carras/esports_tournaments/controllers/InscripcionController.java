package com.carras.esports_tournaments.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carras.esports_tournaments.dto.InscripcionRequestDto;
import com.carras.esports_tournaments.dto.InscripcionResponseDto;
import com.carras.esports_tournaments.dto.RosterRequestDto;
import com.carras.esports_tournaments.services.InscripcionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/inscripciones")
@RequiredArgsConstructor
public class InscripcionController {
    
    private final InscripcionService inscripcionService;

    @PostMapping
    public ResponseEntity<InscripcionResponseDto> crearInscripcion(@Valid @RequestBody InscripcionRequestDto requestDto) {

        InscripcionResponseDto inscripcionResponseDto = inscripcionService.inscribirEquipo(requestDto.getTorneoId(), requestDto.getEquipoId(), requestDto.getUsuarioCreador());

        return ResponseEntity.status(HttpStatus.CREATED).body(inscripcionResponseDto);
    }

    @PutMapping("/{inscripcionId}/roster")
    public ResponseEntity<InscripcionResponseDto> definirRoster(@PathVariable Long inscripcionId, @Valid @RequestBody RosterRequestDto request) {
        
        InscripcionResponseDto inscripcionResponseDto = inscripcionService.definirRoaster(inscripcionId, request.getUsuarioAccionId(), request.getJugadores());

        return ResponseEntity.status(HttpStatus.OK).body(inscripcionResponseDto);
    }
}
