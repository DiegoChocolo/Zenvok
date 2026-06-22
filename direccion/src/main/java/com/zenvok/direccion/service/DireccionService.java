package com.zenvok.direccion.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zenvok.direccion.client.EstadoClient;
import com.zenvok.direccion.client.UsuarioClient;
import com.zenvok.direccion.dto.DireccionRequestDTO;
import com.zenvok.direccion.dto.DireccionResponseDTO;
import com.zenvok.direccion.dto.EstadoResponseDTO;
import com.zenvok.direccion.dto.UsuarioResponseDTO;
import com.zenvok.direccion.exception.ComunaReferenciaInvalidaException;
import com.zenvok.direccion.exception.DireccionNoEncontradaException;
import com.zenvok.direccion.exception.EstadoNoEncontradoException;
import com.zenvok.direccion.exception.UsuarioNoEncontradoException;
import com.zenvok.direccion.model.Comuna;
import com.zenvok.direccion.model.Direccion;
import com.zenvok.direccion.repository.ComunaRepository;
import com.zenvok.direccion.repository.DireccionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DireccionService {

    private final DireccionRepository direccionRepo;
    private final ComunaRepository comunaRepo;
    private final EstadoClient estadoClient;
    private final UsuarioClient usuarioClient;

    private DireccionResponseDTO mapToDTO(Direccion d) {
        return new DireccionResponseDTO(
            d.getId(), d.getCalle(), d.getNumeracion(), 
            d.getBlock(), d.getEstadoId(), d.getComuna().getId(), d.getUsuarioId()
        );
    }

        private void validarEstadoExiste(Long estadoId) {
        log.info("Validando existencia del estado con ID: {} a través de EstadoClient", estadoId);
        try {
            EstadoResponseDTO estado = estadoClient.buscarPorId(estadoId);
            if (estado == null || estado.getId() == null) {
                log.warn("El microservicio de estado devolvió un objeto nulo para el ID: {}", estadoId);
                throw new EstadoNoEncontradoException("Estado no encontrado con ID: " + estadoId);
            }
            log.info("Estado con ID: {} validado correctamente", estadoId);
        } catch (Exception e) {
            log.error("Error al comunicarse con el microservicio de estado para el ID: {}. Motivo: {}", estadoId, e.getMessage());
            throw new EstadoNoEncontradoException("Estado no encontrado con ID: " + estadoId);
        }
    }

    private void validarUsuarioExiste(Long usuarioId) {
        log.info("Validando existencia del usuario con ID: {} a través de UsuarioClient", usuarioId);
        try {
            UsuarioResponseDTO usuario = usuarioClient.buscarPorId(usuarioId);
            if (usuario == null || usuario.getId() == null) {   
                log.warn("El microservicio de usuarios devolvió un objeto nulo para el ID: {}", usuarioId);
                throw new UsuarioNoEncontradoException("Usuario no encontrado con ID: " + usuarioId);

            }
            log.info("Usuario con ID: {} validado correctamente", usuarioId);
        } catch (Exception e) {
            log.error("Error al comunicarse con el microservicio de usuarios para el ID: {}. Motivo: {}", usuarioId, e.getMessage());
            throw new UsuarioNoEncontradoException("Usuario no encontrado con ID: " + usuarioId);
        }
    }

    @Transactional(readOnly = true)
    public List<DireccionResponseDTO> listarDirecciones() {
        log.info("Solicitando listado de todas las direcciones");
        List<DireccionResponseDTO> direcciones = direccionRepo.findAll().stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
        log.info("Listado de direcciones recuperado exitosamente. Total: {}", direcciones.size());
        return direcciones;
    }
    @Transactional(readOnly = true)
    public DireccionResponseDTO obtenerPorId(Long id) {
        log.info("Buscando dirección con id: {}", id);
        return direccionRepo.findById(id)
            .map(this::mapToDTO)
            .orElseThrow(() -> {
                log.warn("No se encontró la dirección con el id: {}", id);
                return new DireccionNoEncontradaException("Direccion no encontrada con el ID: " + id);
            });
    }

    @Transactional
    public DireccionResponseDTO crearDireccion(DireccionRequestDTO dto) {
        log.info("Iniciando creacion de dirección para el usuario: {}",dto.getUsuarioId());
        
        validarEstadoExiste(dto.getEstadoId());
        validarUsuarioExiste(dto.getUsuarioId());   

        Direccion direccion = new Direccion(); 
        Comuna comuna = comunaRepo.findById(dto.getComunaId())
            .orElseThrow(() -> {
                log.warn("Intento fallido de creación. Comuna no encontrada con id: {}", dto.getComunaId());
                return new ComunaReferenciaInvalidaException("Comuna no encontrada con el ID " + dto.getComunaId());
            });
        
        direccion.setCalle(dto.getCalle().trim().toUpperCase());
        direccion.setNumeracion(dto.getNumeracion());
        direccion.setBlock(dto.getBlock()!= null ? dto.getBlock().trim().toUpperCase() : null);
        direccion.setEstadoId(dto.getEstadoId());
        direccion.setComuna(comuna);
        direccion.setUsuarioId(dto.getUsuarioId());
        
        DireccionResponseDTO response = mapToDTO(direccionRepo.save(direccion));
        log.info("Dirección creada exitosamente con id: {} para el usuario id: {}", response.getId(), response.getUsuarioId());
        return response;
    }

    @Transactional
    public DireccionResponseDTO actualizarDireccion(Long id, DireccionRequestDTO dto) {
        log.info("Iniciando actualizacion dirección: {}", id);

        validarEstadoExiste(dto.getEstadoId());
        validarUsuarioExiste(dto.getUsuarioId());

        Direccion direccion = direccionRepo.findById(id)
            .orElseThrow(() -> {
                log.warn("Intento fallido de actualización. Dirección no encontrada con id: {}", id);
                return new DireccionNoEncontradaException("Direccion no encontrada con el ID: " + id);
            });
            
        Comuna comuna = comunaRepo.findById(dto.getComunaId())
        .orElseThrow(() -> {
            log.warn("Intento fallido de actualización. Comuna no encontrada con id: {}", dto.getComunaId());
            return new ComunaReferenciaInvalidaException("Comuna no encontrada con el ID: " + dto.getComunaId());
        });
        direccion.setCalle(dto.getCalle().trim().toUpperCase());
        direccion.setNumeracion(dto.getNumeracion());
        direccion.setBlock(dto.getBlock()!= null ? dto.getBlock().trim().toUpperCase() : null);
        direccion.setEstadoId(dto.getEstadoId());
        direccion.setComuna(comuna);
        direccion.setUsuarioId(dto.getUsuarioId());

        DireccionResponseDTO response = mapToDTO(direccionRepo.save(direccion));
        log.info("Dirección con id: {} actualizada exitosamente", id);
        return response;
    }

    @Transactional
    public void eliminarDireccion(Long id) {
        log.info("Iniciando eliminacion de dirección: {}", id);
        Direccion direccion = direccionRepo.findById(id)
            .orElseThrow(() -> {
                log.warn("Intento fallido de eliminación. Dirección no encontrada con id: {}", id);
                return new DireccionNoEncontradaException("Direccion no encontrada con ID: " + id);
            });
        direccionRepo.delete(direccion);
        log.info("Dirección con ID: {} eliminada exitosamente", id);
    }

}


