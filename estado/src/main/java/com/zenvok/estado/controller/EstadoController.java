package com.zenvok.estado.controller;

import com.zenvok.estado.dto.EstadoRequestDTO;
import com.zenvok.estado.dto.EstadoResponseDTO;
import com.zenvok.estado.service.EstadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estados")
public class EstadoController {

    @Autowired
    private EstadoService estadoService;

    @GetMapping
    public ResponseEntity<List<EstadoResponseDTO>> listarEstados() {
        return ResponseEntity.ok(estadoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadoResponseDTO> buscarEstadoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(estadoService.buscarPorId(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<EstadoResponseDTO>> buscarPorNombre(@RequestParam String nombre){
        return ResponseEntity.ok(estadoService.buscarPorNombre(nombre));
    }

    @PostMapping
    public ResponseEntity<EstadoResponseDTO> crearEstado(
            @Valid @RequestBody EstadoRequestDTO request
    ) {
        EstadoResponseDTO nuevo = estadoService.guardar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadoResponseDTO> actualizarEstado(
            @PathVariable Long id,
            @Valid @RequestBody EstadoRequestDTO request
    ) {
        return ResponseEntity.ok(estadoService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEstado(@PathVariable Long id) {
        estadoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}