package com.zenvok.configuracion.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zenvok.configuracion.dto.ConfiguracionDTO;
import com.zenvok.configuracion.service.ConfiguracionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/configuraciones")
@RequiredArgsConstructor
public class ConfiguracionController {

    private final ConfiguracionService configuracionService;

    @PostMapping
    public ResponseEntity<ConfiguracionDTO> crearConfiguracion(@Valid @RequestBody ConfiguracionDTO dto) {
        ConfiguracionDTO nueva = configuracionService.guardarConfiguracion(dto);
        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ConfiguracionDTO>> listarTodas() {
        return ResponseEntity.ok(configuracionService.obtenerTodas());
    }

    @GetMapping("/clave/{clave}")
    public ResponseEntity<ConfiguracionDTO> buscarPorClave(@PathVariable String clave) {
        return configuracionService.obtenerPorClave(clave)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/clave/{clave}")
    public ResponseEntity<ConfiguracionDTO> actualizarConfiguracion(@PathVariable String clave, @Valid @RequestBody ConfiguracionDTO dto) {
        return ResponseEntity.ok(configuracionService.modificarConfiguracion(clave, dto));
    }
}
    

