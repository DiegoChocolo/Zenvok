package com.zenvok.gestionusuario.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zenvok.gestionusuario.dto.TipoUsuarioDTO;
import com.zenvok.gestionusuario.service.TipoUsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tipos_usuarios")
@RequiredArgsConstructor
public class TipoUsuarioController {

    private final TipoUsuarioService tipoUsuarioService;

    @PostMapping
    public ResponseEntity<TipoUsuarioDTO> crearTipoUsuario(@RequestBody TipoUsuarioDTO dto) {
        TipoUsuarioDTO nuevoTipo = tipoUsuarioService.crearTipo(dto);
        return new ResponseEntity<>(nuevoTipo, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TipoUsuarioDTO>> obtenerTodos() {
        List<TipoUsuarioDTO> lista = tipoUsuarioService.obtenerTodosLosTipos();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoUsuarioDTO> obtenerPorId(@PathVariable Long id) {
        return tipoUsuarioService.obtenerTipoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
