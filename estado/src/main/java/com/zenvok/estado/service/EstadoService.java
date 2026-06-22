package com.zenvok.estado.service;

import com.zenvok.estado.dto.EstadoRequestDTO;
import com.zenvok.estado.dto.EstadoResponseDTO;
import com.zenvok.estado.exception.EstadoNotFoundException;
import com.zenvok.estado.model.Estado;
import com.zenvok.estado.repository.EstadoRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class EstadoService {

    private static final Logger logger = LoggerFactory.getLogger(EstadoService.class);

    @Autowired
    private EstadoRepository estadoRepository;

    @Transactional(readOnly = true)
    public List<EstadoResponseDTO> listar() {
        logger.info("Listando estados");

        return estadoRepository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public EstadoResponseDTO buscarPorId(Long id) {
        logger.info("Buscando estado con id: {}", id);

        Estado estado = estadoRepository.findById(id)
                .orElseThrow(() -> new EstadoNotFoundException(id));

        return convertirAResponse(estado);
    }

    @Transactional(readOnly = true)
    public List<EstadoResponseDTO> buscarPorNombre(String nombre){
        logger.info("Buscando estados por nombre: {}", nombre);

        return estadoRepository.findByNombreContainingIgnoreCase(nombre)
        .stream()
        .map(this::convertirAResponse)
        .toList();
    }

    @Transactional
    public EstadoResponseDTO guardar(EstadoRequestDTO request) {
        logger.info("Guardando nuevo estado: {}", request.getNombre());

        Estado estado = new Estado();

        estado.setNombre(request.getNombre());
        estado.setDescripcion(request.getDescripcion());

        Estado guardado = estadoRepository.save(estado);

        return convertirAResponse(guardado);
    }

    @Transactional
    public EstadoResponseDTO actualizar(Long id, EstadoRequestDTO request) {
        logger.info("Actualizando estado con id: {}", id);

        Estado estado = estadoRepository.findById(id)
                .orElseThrow(() -> new EstadoNotFoundException(id));

        estado.setNombre(request.getNombre());
        estado.setDescripcion(request.getDescripcion());

        Estado actualizado = estadoRepository.save(estado);

        return convertirAResponse(actualizado);
    }

    @Transactional
    public void eliminar(Long id) {
        logger.info("Eliminandp estado con id: {}", id);

        Estado estado = estadoRepository.findById(id)
                .orElseThrow(() -> new EstadoNotFoundException(id));

        estadoRepository.delete(estado);
    }

    // Convertir Entity a ResponseDTO
    private EstadoResponseDTO convertirAResponse(Estado estado) {
        EstadoResponseDTO dto = new EstadoResponseDTO();

        dto.setId(estado.getId());
        dto.setNombre(estado.getNombre());
        dto.setDescripcion(estado.getDescripcion());

        return dto;
    }
}