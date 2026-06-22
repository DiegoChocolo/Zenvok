package com.zenvok.Ventas.controller;

import com.zenvok.Ventas.dto.VentaRequestDTO;
import com.zenvok.Ventas.dto.VentaResponseDTO;
import com.zenvok.Ventas.service.VentasService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
public class VentasController {

    private final VentasService ventasService;

    @PostMapping
    public ResponseEntity<VentaResponseDTO> crearVenta(@Valid @RequestBody VentaRequestDTO request) {
        VentaResponseDTO nuevaVenta = ventasService.guardar(request);
        return new ResponseEntity<>(nuevaVenta, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<VentaResponseDTO>> listarTodas() {
        List<VentaResponseDTO> ventas = ventasService.obtenerTodas();
        return ResponseEntity.ok(ventas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ventasService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}