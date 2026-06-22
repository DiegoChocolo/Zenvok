package com.zenvok.Gest_Bode.service;

import com.zenvok.Gest_Bode.dto.PasilloRequestDTO;
import com.zenvok.Gest_Bode.dto.PasilloResponseDTO;
import com.zenvok.Gest_Bode.model.Pasillo;
import com.zenvok.Gest_Bode.repository.PasilloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PasilloService {

    private final PasilloRepository pasilloRepository;

    public PasilloResponseDTO guardar(PasilloRequestDTO dto) {
        Pasillo pasillo = new Pasillo();
        pasillo.setNombrePasillo(dto.getNombrePasillo());
        
        Pasillo guardado = pasilloRepository.save(pasillo);
        return mapearAResponse(guardado);
    }

    public List<PasilloResponseDTO> obtenerTodos() {
        return pasilloRepository.findAll()
                .stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    public Optional<PasilloResponseDTO> obtenerPorId(Long id) {
        return pasilloRepository.findById(id)
                .map(this::mapearAResponse);
    }

    public List<PasilloResponseDTO> buscarPorNombre(String nombre) {
        return pasilloRepository.buscarPorNombreParecido(nombre)
                .stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    private PasilloResponseDTO mapearAResponse(Pasillo pasillo) {
        return new PasilloResponseDTO(pasillo.getIdPasillo(), pasillo.getNombrePasillo());
    }
}
