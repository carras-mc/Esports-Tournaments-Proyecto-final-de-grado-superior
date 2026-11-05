package com.carras.esports_tournaments.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.carras.esports_tournaments.dto.TorneoRequestDto;
import com.carras.esports_tournaments.dto.TorneoResponseDto;
import com.carras.esports_tournaments.services.TorneoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/torneos")
@RequiredArgsConstructor
public class TorneoController {
    
    private final TorneoService torneoService;

    @GetMapping("/{id}") // Usamos GET y definimos el parámetro en la ruta
    public ResponseEntity<TorneoResponseDto> obtenerTorneoConDetalles(@PathVariable("id") Long id) {
        TorneoResponseDto response = torneoService.obtenerTorneoConDetalles(id);
        
        // Devolvemos 200 OK (HttpStatus.OK), que es el estándar para búsquedas exitosas
        return ResponseEntity.ok(response); 
    }

    @PostMapping
    public ResponseEntity<TorneoResponseDto> crearTorneo(@Valid @RequestBody TorneoRequestDto request) {
        TorneoResponseDto response = torneoService.crearTorneo(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
