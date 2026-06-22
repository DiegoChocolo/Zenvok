package com.zenvok.Gest_Bode.service;

import com.zenvok.Gest_Bode.dto.PasEstRequestDTO;
import com.zenvok.Gest_Bode.dto.PasEstResponseDTO;
import com.zenvok.Gest_Bode.model.Estante;
import com.zenvok.Gest_Bode.model.Pasillo;
import com.zenvok.Gest_Bode.model.Pas_Est;
import com.zenvok.Gest_Bode.repository.EstanteRepository;
import com.zenvok.Gest_Bode.repository.PasilloRepository;
import com.zenvok.Gest_Bode.repository.PasEstRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PasEstService {

    private final PasEstRepository pasEstRepository;
    private final PasilloRepository pasilloRepository;
    private final EstanteRepository estanteRepository;

    public PasEstResponseDTO guardar(PasEstRequestDTO dto) {
        Pasillo pasillo = pasilloRepository.findById(dto.getPasilloId())
                .orElseThrow(() -> new RuntimeException("No se encontró el Pasillo con ID: " + dto.getPasilloId()));

        Estante estante = estanteRepository.findById(dto.getEstanteId())
                .orElseThrow(() -> new RuntimeException("No se encontró el Estante con ID: " + dto.getEstanteId()));

        Pas_Est pasEst = new Pas_Est();
        pasEst.setPasillo(pasillo);
        pasEst.setEstante(estante);

        Pas_Est guardado = pasEstRepository.save(pasEst);
        return mapearAResponse(guardado);
    }

    public List<PasEstResponseDTO> obtenerTodos() {
        return pasEstRepository.findAll()
                .stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    public Optional<PasEstResponseDTO> obtenerPorId(Long id) {
        return pasEstRepository.findById(id)
                .map(this::mapearAResponse);
    }

    public List<PasEstResponseDTO> listarEstantesPorPasillo(Long idPasillo) {
        return pasEstRepository.findByPasilloIdPasillo(idPasillo)
                .stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    private PasEstResponseDTO mapearAResponse(Pas_Est entities) {
        PasEstResponseDTO res = new PasEstResponseDTO();
        res.setIdPasEst(entities.getIdPasEst());
        res.setPasilloId(entities.getPasillo().getIdPasillo());
        res.setNombrePasillo(entities.getPasillo().getNombrePasillo());
        res.setEstanteId(entities.getEstante().getIdEstante());
        res.setNombreEstante(entities.getEstante().getNombreEstante());
        return res;
    }
}
