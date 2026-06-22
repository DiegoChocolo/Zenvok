package com.zenvok.configuracion.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.zenvok.configuracion.dto.ConfiguracionDTO;
import com.zenvok.configuracion.model.Configuracion;
import com.zenvok.configuracion.repository.ConfiguracionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConfiguracionService {

    private final ConfiguracionRepository configuracionRepository;

    private ConfiguracionDTO mapToDTO(Configuracion config) {
        ConfiguracionDTO dto = new ConfiguracionDTO();
        dto.setId(config.getId());
        dto.setClave(config.getClave());
        dto.setValor(config.getValor());
        return dto;
    }

    public ConfiguracionDTO guardarConfiguracion(ConfiguracionDTO dto) {
        if (configuracionRepository.findByClave(dto.getClave()).isPresent()) {
            throw new RuntimeException("La clave '" + dto.getClave() + "' ya existe.");
        }
        Configuracion config = new Configuracion();
        config.setClave(dto.getClave());
        config.setValor(dto.getValor());

        return mapToDTO(configuracionRepository.save(config));
    }

    public List<ConfiguracionDTO> obtenerTodas() {
        return configuracionRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ConfiguracionDTO> obtenerPorClave(String clave) {
        return configuracionRepository.findByClave(clave).map(this::mapToDTO);
    }

    public ConfiguracionDTO modificarConfiguracion(String clave, ConfiguracionDTO dto) {
        Configuracion configExistente = configuracionRepository.findByClave(clave)
                .orElseThrow(() -> new RuntimeException("La clave '" + clave + "' no existe."));

        configExistente.setValor(dto.getValor());
        return mapToDTO(configuracionRepository.save(configExistente));
    }
}
