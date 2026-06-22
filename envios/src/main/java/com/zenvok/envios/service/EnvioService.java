package com.zenvok.envios.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zenvok.envios.client.DireccionClient;
import com.zenvok.envios.client.EstadoClient;
import com.zenvok.envios.client.VentaClient;
import com.zenvok.envios.dto.EnvioRequest;
import com.zenvok.envios.dto.EnvioResponse;
import com.zenvok.envios.exception.DireccionNoDisponibleException;
import com.zenvok.envios.exception.EnvioNoEncontradoException;
import com.zenvok.envios.exception.EstadoNoDisponibleException;
import com.zenvok.envios.exception.VentaNoDisponibleException;
import com.zenvok.envios.model.Envio;
import com.zenvok.envios.repository.EnvioRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnvioService {

    private final EnvioRepository envioRepo;
    private final DireccionClient direccionClient;
    private final VentaClient ventaClient;
    private final EstadoClient estadoClient;

    private void validarDireccionId(Long direccionId) {
        try {
            direccionClient.buscarPorId(direccionId);
        } catch (Exception e) {
            throw new DireccionNoDisponibleException(
                "La dirección con ID " + direccionId + " no existe o no se pudo conectar con MS Dirección");
        }
    }

    private void validarVentaId(Long ventaId) {
        try {
            ventaClient.buscarPorId(ventaId);
        } catch (Exception e) {
            throw new VentaNoDisponibleException(
                "La venta con ID " + ventaId + " no existe o no se pudo conectar con MS Venta");
        }
    }

    private void validarEstadoId(Long estadoId) {
        try {
            estadoClient.buscarPorId(estadoId);
        } catch (Exception e) {
            throw new EstadoNoDisponibleException(
                "El estado con ID " + estadoId + " no existe o no se pudo conectar con MS Estado");
        }
    }

    private EnvioResponse mapToDTO(Envio e) {
        return new EnvioResponse(
                e.getId(),
                e.getFechaEnvio(),
                e.getFechaEmbargue(),
                e.getComentarios(),
                e.getDireccionId(),
                e.getVentaId(),
                e.getEstadoId()
        );
    }

    @Transactional(readOnly = true)
    public List<EnvioResponse> listarEnvios() {
        return envioRepo.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EnvioResponse obtenerPorId(Long id) {
        return envioRepo.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new EnvioNoEncontradoException("Envío no encontrado con id: " + id));
    }

    @Transactional(readOnly = true)
    public List<EnvioResponse> buscarPorVenta(Long ventaId) {
        return envioRepo.findByVentaId(ventaId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EnvioResponse> buscarPorDireccion(Long direccionId) {
        return envioRepo.findByDireccionId(direccionId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EnvioResponse> buscarPorEstado(Long estadoId) {
        return envioRepo.findByEstadoId(estadoId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public EnvioResponse crearEnvio(EnvioRequest dto) {
        log.info("Creando envío para dirección {}", dto.getDireccionId());

        validarDireccionId(dto.getDireccionId());
        validarVentaId(dto.getVentaId());
        validarEstadoId(dto.getEstadoId());

        Envio envio = new Envio(
                null,
                dto.getFechaEnvio(),
                dto.getFechaEmbargue(),
                dto.getComentarios(),
                dto.getDireccionId(),
                dto.getVentaId(),
                dto.getEstadoId()
        );

        EnvioResponse response = mapToDTO(envioRepo.save(envio));
        log.info("Envío creado exitosamente con id {}", response.getId());
        return response;
    }

    @Transactional
    public EnvioResponse actualizarEnvio(Long id, EnvioRequest dto) {
        log.info("Actualizando envío {}", id);

        Envio envio = envioRepo.findById(id)
                .orElseThrow(() -> new EnvioNoEncontradoException("Envío no encontrado con id: " + id));

        validarDireccionId(dto.getDireccionId());
        validarVentaId(dto.getVentaId());
        validarEstadoId(dto.getEstadoId());

        envio.setFechaEnvio(dto.getFechaEnvio());
        envio.setFechaEmbargue(dto.getFechaEmbargue());
        envio.setComentarios(dto.getComentarios());
        envio.setDireccionId(dto.getDireccionId());
        envio.setVentaId(dto.getVentaId());
        envio.setEstadoId(dto.getEstadoId());

        log.info("Envío {} actualizado exitosamente", id);
        return mapToDTO(envioRepo.save(envio));
    }

    @Transactional
    public void eliminarEnvio(Long id) {
        log.info("Eliminando envío {}", id);

        Envio envio = envioRepo.findById(id)
                .orElseThrow(() -> new EnvioNoEncontradoException("Envío no encontrado con id: " + id));

        envioRepo.delete(envio);
        log.info("Envío {} eliminado con éxito", id);
    }
}