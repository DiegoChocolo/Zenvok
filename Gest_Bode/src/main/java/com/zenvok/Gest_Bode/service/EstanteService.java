package com.zenvok.Gest_Bode.service;

import com.zenvok.Gest_Bode.dto.EstanteRequestDTO;
import com.zenvok.Gest_Bode.dto.EstanteResponseDTO;
import com.zenvok.Gest_Bode.model.Estante;
import com.zenvok.Gest_Bode.repository.EstanteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstanteService {

    private final EstanteRepository estanteRepository;

    public EstanteResponseDTO guardar(EstanteRequestDTO dto) {
        Estante estante = new Estante();
        estante.setNombreEstante(dto.getNombreEstante());
        
        Estante guardado = estanteRepository.save(estante);
        return mapearAResponse(guardado);
    }

    public List<EstanteResponseDTO> obtenerTodos() {
        return estanteRepository.findAll()
                .stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    public Optional<EstanteResponseDTO> obtenerPorId(Long id) {
        return estanteRepository.findById(id)
                .map(this::mapearAResponse);
    }

    public List<EstanteResponseDTO> buscarPorNombre(String nombre) {
        return estanteRepository.buscarPorNombreParecido(nombre)
                .stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    private EstanteResponseDTO mapearAResponse(Estante estante) {
        return new EstanteResponseDTO(estante.getIdEstante(), estante.getNombreEstante());
    }
}