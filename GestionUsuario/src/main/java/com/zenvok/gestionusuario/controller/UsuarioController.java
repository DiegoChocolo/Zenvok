package com.zenvok.gestionusuario.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zenvok.gestionusuario.dto.UsuarioRequestDTO;
import com.zenvok.gestionusuario.dto.UsuarioResponseDTO;
import com.zenvok.gestionusuario.service.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor

public class UsuarioController {

    private final UsuarioService usuarioService;


    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> registrarUsuario(@Valid @RequestBody UsuarioRequestDTO request) {
        UsuarioResponseDTO nuevoUsuario = usuarioService.crearUsuario(request);
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodosLosUsuarios() {
        List<UsuarioResponseDTO> lista = usuarioService.obtenerTodosLosUsuarios();
        return ResponseEntity.ok(lista);
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        return usuarioService.obtenerUsuarioPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tipo/{idTipo}")
    public ResponseEntity<List<UsuarioResponseDTO>> listarPorTipo(@PathVariable Long idTipo) {
        List<UsuarioResponseDTO> usuarios = usuarioService.obtenerUsuariosPorTipo(idTipo);
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizarUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioRequestDTO request) {
        UsuarioResponseDTO modificado = usuarioService.modificarUsuario(id, request);
        return ResponseEntity.ok(modificado);
    }

    @DeleteMapping("/rut/{rut}")
    public ResponseEntity<String> eliminarPorRut(@PathVariable String rut) {
        usuarioService.eliminarUsuarioPorRut(rut);
        return ResponseEntity.ok("Usuario con RUT " + rut + " fue eliminado exitosamente.");
    }

}

