package com.zenvok.Gest_Bode.controller;

import com.zenvok.Gest_Bode.dto.PasilloRequestDTO;
import com.zenvok.Gest_Bode.dto.PasilloResponseDTO;
import com.zenvok.Gest_Bode.service.PasilloService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pasillos")
@RequiredArgsConstructor
public class PasilloController {

    private final PasilloService pasilloService;

    @PostMapping
    public ResponseEntity<PasilloResponseDTO> crear(@Valid @RequestBody PasilloRequestDTO request) {
        return new ResponseEntity<>(pasilloService.guardar(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PasilloResponseDTO>> listarTodos() {
        return ResponseEntity.ok(pasilloService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PasilloResponseDTO> buscarPorId(@PathVariable Long id) {
        return pasilloService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<PasilloResponseDTO>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(pasilloService.buscarPorNombre(nombre));
    }
}
