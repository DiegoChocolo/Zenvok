package com.zenvok.direccion.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zenvok.direccion.dto.ComunaRequestDTO;
import com.zenvok.direccion.dto.ComunaResponseDTO;
import com.zenvok.direccion.exception.ComunaDuplicadaException;
import com.zenvok.direccion.exception.ComunaNoEncontradaException;
import com.zenvok.direccion.model.Comuna;
import com.zenvok.direccion.repository.ComunaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@RequiredArgsConstructor
public class ComunaService {
    
    private final ComunaRepository comunaRepo;

    private ComunaResponseDTO mapToDTO(Comuna c) {
        String nombreMayuscula = (c.getNombreComuna()!= null) ? c.getNombreComuna().toUpperCase(): null;
        return new ComunaResponseDTO(c.getId(), nombreMayuscula);
    }

    @Transactional(readOnly = true)
    public List<ComunaResponseDTO> listarComunas() {
        log.info("Solicitando listado de todas las comunas");
        List<ComunaResponseDTO> comunas = comunaRepo.findAll().stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
        log.info("Listado de comunas recuperado exitosamente. Total: {}", comunas.size());
        return comunas;
    }

    @Transactional(readOnly = true)
    public ComunaResponseDTO obtenerPorId(Long id) {
        log.info("Buscando comuna con id: {}", id);
        return comunaRepo.findById(id)
            .map(this::mapToDTO)
            .orElseThrow(() -> {
                log.warn("No se encontró la comuna con id: {}", id);                
                return new ComunaNoEncontradaException("Comuna no encontrada con id: " + id);
            });
    }

    @Transactional(readOnly = true)
    public ComunaResponseDTO buscarPorNombre(String nombreComuna) {
        log.info("Buscando comuna por nombre: {}", nombreComuna);
        return comunaRepo.findByNombreComunaIgnoreCase(nombreComuna)
            .map(this::mapToDTO)
            .orElseThrow(() -> {
                log.warn("Comuna no encontrada con nombre: {}", nombreComuna);
                return new ComunaNoEncontradaException("Comuna no encontrada con nombre: " + nombreComuna);
            });
    }

    @Transactional
    public ComunaResponseDTO crearComuna(ComunaRequestDTO dto) {
        log.info("Creando comuna: {}", dto.getNombreComuna());
        if (comunaRepo.findByNombreComunaIgnoreCase(dto.getNombreComuna()).isPresent()) {
            log.warn("Intento fallido de crear comuna. Ya existe una comuna con el nombre: {}", dto.getNombreComuna());
            throw new ComunaDuplicadaException("Ya existe una comuna con el nombre: " + dto.getNombreComuna());
        }

        Comuna comuna = new Comuna();
        comuna.setNombreComuna(dto.getNombreComuna().trim().toUpperCase());

        ComunaResponseDTO response = mapToDTO(comunaRepo.save(comuna));
        log.info("Comuna creada exitosamente con id: {}", response.getId());
        return response;
    }

    @Transactional
    public ComunaResponseDTO actualizarComuna(Long id, ComunaRequestDTO dto) {
        log.info("Actualizando comuna con id: {}", id);

        Comuna comuna = comunaRepo.findById(id)
            .orElseThrow(() -> {
                log.warn("Intento fallido de actualización. Comuna no encontrada con id: {}", id);
                return new ComunaNoEncontradaException("Comuna no encontrada con id: " + id);
            });

        comunaRepo.findByNombreComunaIgnoreCase(dto.getNombreComuna())
            .ifPresent(c -> {
                if (!c.getId().equals(id)) {
                    log.warn("Intento fallido de actualización. El nombre '{}' ya está ocupado por la comuna id: {}", dto.getNombreComuna(), c.getId());
                    throw new ComunaDuplicadaException("Ya existe una comuna con el nombre: " + dto.getNombreComuna());
                }
            });

        comuna.setNombreComuna(dto.getNombreComuna().trim().toUpperCase());

        ComunaResponseDTO response = mapToDTO(comunaRepo.save(comuna));
        log.info("Comuna actualizada exitosamente con id: {}", id);
        return response;
    }

    @Transactional
    public void eliminarComuna(Long id) {
        log.info("Eliminando comuna con id: {}", id);
        Comuna comuna = comunaRepo.findById(id)
            .orElseThrow(() -> {
                log.warn("Intento fallido de eliminación. Comuna no encontrada con id: {}", id);
                return new ComunaNoEncontradaException("Comuna no encontrada con id: " + id);
            });
        comunaRepo.delete(comuna);
        log.info("Comuna eliminada exitosamente con id: {}", id);
    }
}