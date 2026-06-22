package com.zenvok.gestionusuario.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.zenvok.gestionusuario.dto.TipoUsuarioDTO;
import com.zenvok.gestionusuario.model.TipoUsuario;
import com.zenvok.gestionusuario.repository.TipoUsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TipoUsuarioService {

    private final TipoUsuarioRepository tipoUsuarioRepository;


    private TipoUsuarioDTO mapToDTO(TipoUsuario tipo) {
        TipoUsuarioDTO dto = new TipoUsuarioDTO();
        dto.setId(tipo.getId());
        dto.setNombreTipo(tipo.getNombreTipo());
        return dto;
    }

    public TipoUsuarioDTO crearTipo(TipoUsuarioDTO dto) {
        TipoUsuario tipo = new TipoUsuario();
        tipo.setNombreTipo(dto.getNombreTipo());
        
        TipoUsuario nuevoTipo = tipoUsuarioRepository.save(tipo);
        return mapToDTO(nuevoTipo);
    }


    public List<TipoUsuarioDTO> obtenerTodosLosTipos() {
        return tipoUsuarioRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public java.util.Optional<TipoUsuarioDTO> obtenerTipoPorId(Long id) {
        return tipoUsuarioRepository.findById(id).map(this::mapToDTO);
    }

}
