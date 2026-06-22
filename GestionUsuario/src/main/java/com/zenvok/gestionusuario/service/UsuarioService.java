package com.zenvok.gestionusuario.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zenvok.gestionusuario.dto.UsuarioRequestDTO;
import com.zenvok.gestionusuario.dto.UsuarioResponseDTO;
import com.zenvok.gestionusuario.model.TipoUsuario;
import com.zenvok.gestionusuario.model.Usuario;
import com.zenvok.gestionusuario.repository.TipoUsuarioRepository;
import com.zenvok.gestionusuario.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final TipoUsuarioRepository tipoUsuarioRepository;

    private UsuarioResponseDTO mapToDTO(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setRut(usuario.getRut());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setCorreo(usuario.getCorreo());

        if (usuario.getTipoUsuario() != null) {
            dto.setNombreRol(usuario.getTipoUsuario().getNombreTipo());
        }

        return dto;
    }

    @Transactional
    public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO request) {
        TipoUsuario tipo = tipoUsuarioRepository.findById(request.getIdTipoUsuario())
                .orElseThrow(() -> new RuntimeException("El tipo de usuario con ID: " + request.getIdTipoUsuario() + " no existe."));

        Usuario usuario = new Usuario();
        usuario.setRut(request.getRut());
        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setCorreo(request.getCorreo());
        usuario.setContrasena(request.getContrasena());
        usuario.setTipoUsuario(tipo);

        Usuario nuevoUsuario = usuarioRepository.save(usuario);
        return mapToDTO(nuevoUsuario);
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<UsuarioResponseDTO> obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id).map(this::mapToDTO);
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> obtenerUsuariosPorTipo(Long idTipo) {
        return usuarioRepository.listarPorTipo(idTipo)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public UsuarioResponseDTO modificarUsuario(Long id, UsuarioRequestDTO request) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se puede modificar. Usuario con ID " + id + " no existe."));

        TipoUsuario tipo = tipoUsuarioRepository.findById(request.getIdTipoUsuario())
                .orElseThrow(() -> new RuntimeException("El tipo de usuario con ID: " + request.getIdTipoUsuario() + " no existe."));

        usuarioExistente.setRut(request.getRut());
        usuarioExistente.setNombre(request.getNombre());
        usuarioExistente.setApellido(request.getApellido());
        usuarioExistente.setCorreo(request.getCorreo());
        usuarioExistente.setContrasena(request.getContrasena());
        usuarioExistente.setTipoUsuario(tipo);

        Usuario usuarioActualizado = usuarioRepository.save(usuarioExistente);
        return mapToDTO(usuarioActualizado);
    }

    @Transactional
    public void eliminarUsuarioPorRut(String rut) {
        Usuario usuario = usuarioRepository.buscarPorRut(rut)
                .orElseThrow(() -> new RuntimeException("No se puede eliminar. El usuario con RUT " + rut + " no existe."));

        usuarioRepository.delete(usuario);
    }
}