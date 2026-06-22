package com.zenvok.Gest_Bode.controller;

import com.zenvok.Gest_Bode.dto.EstanteRequestDTO;
import com.zenvok.Gest_Bode.dto.EstanteResponseDTO;
import com.zenvok.Gest_Bode.service.EstanteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estantes")
@RequiredArgsConstructor
public class EstanteController {

    private final EstanteService estanteService;

    @PostMapping
    public ResponseEntity<EstanteResponseDTO> crear(@Valid @RequestBody EstanteRequestDTO request) {
        return new ResponseEntity<>(estanteService.guardar(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EstanteResponseDTO>> listarTodos() {
        return ResponseEntity.ok(estanteService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstanteResponseDTO> buscarPorId(@PathVariable Long id) {
        return estanteService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<EstanteResponseDTO>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(estanteService.buscarPorNombre(nombre));
    }
}
