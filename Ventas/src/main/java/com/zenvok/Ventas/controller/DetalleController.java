package com.zenvok.Ventas.controller;
import com.zenvok.Ventas.dto.DetalleRequestDTO;
import com.zenvok.Ventas.dto.DetalleResponseDTO;
import com.zenvok.Ventas.service.DetalleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalles")
@RequiredArgsConstructor
public class DetalleController {
    private final DetalleService detalleService;

    @PostMapping
    public ResponseEntity<DetalleResponseDTO> crearDetalle(@Valid @RequestBody DetalleRequestDTO request) {
        DetalleResponseDTO nuevoDetalle = detalleService.guardar(request);
        return new ResponseEntity<>(nuevoDetalle, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DetalleResponseDTO>> listarTodos() {
        List<DetalleResponseDTO> detalles = detalleService.obtenerTodos();
        return ResponseEntity.ok(detalles);
    }

    @GetMapping("/venta/{idVenta}")
    public ResponseEntity<List<DetalleResponseDTO>> listarPorVenta(@PathVariable Long idVenta) {
        List<DetalleResponseDTO> detallesFiltrados = detalleService.obtenerDetallesPorVenta(idVenta);
        return ResponseEntity.ok(detallesFiltrados);
    }
}