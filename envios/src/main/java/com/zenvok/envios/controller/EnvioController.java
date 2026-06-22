package com.zenvok.envios.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.zenvok.envios.dto.EnvioRequest;
import com.zenvok.envios.dto.EnvioResponse;
import com.zenvok.envios.service.EnvioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/envios")
@RequiredArgsConstructor
public class EnvioController {

    private final EnvioService envioService;

    @GetMapping
    public ResponseEntity<List<EnvioResponse>> listarEnvios() {
        return ResponseEntity.ok(envioService.listarEnvios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnvioResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(envioService.obtenerPorId(id));
    }

    @GetMapping("/venta/{ventaId}")
    public ResponseEntity<List<EnvioResponse>> buscarPorVenta(@PathVariable Long ventaId) {
        return ResponseEntity.ok(envioService.buscarPorVenta(ventaId));
    }

    @GetMapping("/direccion/{direccionId}")
    public ResponseEntity<List<EnvioResponse>> buscarPorDireccion(@PathVariable Long direccionId) {
        return ResponseEntity.ok(envioService.buscarPorDireccion(direccionId));
    }

    @GetMapping("/estado/{estadoId}")
    public ResponseEntity<List<EnvioResponse>> buscarPorEstado(@PathVariable Long estadoId) {
        return ResponseEntity.ok(envioService.buscarPorEstado(estadoId));
    }

    @PostMapping
    public ResponseEntity<EnvioResponse> crearEnvio(@Valid @RequestBody EnvioRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(envioService.crearEnvio(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnvioResponse> actualizarEnvio(
        @PathVariable Long id,
        @Valid @RequestBody EnvioRequest dto
    ) {
        return ResponseEntity.ok(envioService.actualizarEnvio(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEnvio(@PathVariable Long id) {
        envioService.eliminarEnvio(id);
        return ResponseEntity.noContent().build();
    }
}
