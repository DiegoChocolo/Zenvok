package com.zenvok.envios.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

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

@ExtendWith(MockitoExtension.class)
@DisplayName("Test Unitario de EnvioService")
public class EnvioServiceTest {

    @Mock private EnvioRepository envioRepo;
    @Mock private DireccionClient direccionClient;
    @Mock private VentaClient ventaClient;
    @Mock private EstadoClient estadoClient;

    @InjectMocks
    private EnvioService envioService;

    private EnvioRequest requestDto;
    private Envio envioGuardado;

    @BeforeEach
    void setUp() {
        requestDto = new EnvioRequest();
        requestDto.setFechaEnvio("2026-06-21");
        requestDto.setFechaEmbargue("2026-06-22");
        requestDto.setComentarios("Test comentario");
        requestDto.setDireccionId(1L);
        requestDto.setVentaId(10L);
        requestDto.setEstadoId(2L);

        envioGuardado = new Envio(
            5L, "2026-06-21", "2026-06-22", "Test comentario", 1L, 10L, 2L
        );
    }

    // ── crearEnvio ──

    @Test
    @DisplayName("crearEnvio() guarda exitosamente cuando todas las validaciones pasan (Camino Feliz)")
    void crearEnvio_DebeGuardar_CuandoTodoEsValido() {
        when(direccionClient.buscarPorId(1L)).thenReturn(null);
        when(ventaClient.buscarPorId(10L)).thenReturn(null);
        when(estadoClient.buscarPorId(2L)).thenReturn(null);
        when(envioRepo.save(any(Envio.class))).thenReturn(envioGuardado);

        EnvioResponse resultado = envioService.crearEnvio(requestDto);

        assertNotNull(resultado);
        assertEquals(5L, resultado.getId());
        assertEquals("2026-06-21", resultado.getFechaEnvio());
        assertEquals(1L, resultado.getDireccionId());
        verify(envioRepo, times(1)).save(any(Envio.class));
    }

    @Test
    @DisplayName("crearEnvio() lanza DireccionNoDisponibleException si DireccionClient falla")
    void crearEnvio_DebeLanzarException_CuandoDireccionNoDisponible() {
        when(direccionClient.buscarPorId(1L)).thenThrow(new RuntimeException("connection refused"));

        assertThrows(DireccionNoDisponibleException.class, () ->
            envioService.crearEnvio(requestDto)
        );
    }

    @Test
    @DisplayName("crearEnvio() lanza VentaNoDisponibleException si VentaClient falla")
    void crearEnvio_DebeLanzarException_CuandoVentaNoDisponible() {
        when(direccionClient.buscarPorId(1L)).thenReturn(null);
        when(ventaClient.buscarPorId(10L)).thenThrow(new RuntimeException("connection refused"));

        assertThrows(VentaNoDisponibleException.class, () ->
            envioService.crearEnvio(requestDto)
        );
    }

    @Test
    @DisplayName("crearEnvio() lanza EstadoNoDisponibleException si EstadoClient falla")
    void crearEnvio_DebeLanzarException_CuandoEstadoNoDisponible() {
        when(direccionClient.buscarPorId(1L)).thenReturn(null);
        when(ventaClient.buscarPorId(10L)).thenReturn(null);
        when(estadoClient.buscarPorId(2L)).thenThrow(new RuntimeException("connection refused"));

        assertThrows(EstadoNoDisponibleException.class, () ->
            envioService.crearEnvio(requestDto)
        );
    }


    @Test
    @DisplayName("listarEnvios() retorna la lista de todos los envíos")
    void listarEnvios_DebeRetornarLista() {
        when(envioRepo.findAll()).thenReturn(List.of(envioGuardado));

        List<EnvioResponse> resultado = envioService.listarEnvios();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(5L, resultado.get(0).getId());
    }


    @Test
    @DisplayName("obtenerPorId() retorna el envío cuando existe")
    void obtenerPorId_DebeRetornarEnvio_CuandoExiste() {
        when(envioRepo.findById(5L)).thenReturn(Optional.of(envioGuardado));

        EnvioResponse resultado = envioService.obtenerPorId(5L);

        assertNotNull(resultado);
        assertEquals(5L, resultado.getId());
    }

    @Test
    @DisplayName("obtenerPorId() lanza EnvioNoEncontradoException cuando no existe")
    void obtenerPorId_DebeLanzarException_CuandoNoExiste() {
        when(envioRepo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EnvioNoEncontradoException.class, () ->
            envioService.obtenerPorId(99L)
        );
    }


    @Test
    @DisplayName("actualizarEnvio() actualiza correctamente cuando todo es válido")
    void actualizarEnvio_DebeActualizar_CuandoTodoEsValido() {
        when(envioRepo.findById(5L)).thenReturn(Optional.of(envioGuardado));
        when(direccionClient.buscarPorId(1L)).thenReturn(null);
        when(ventaClient.buscarPorId(10L)).thenReturn(null);
        when(estadoClient.buscarPorId(2L)).thenReturn(null);
        when(envioRepo.save(any(Envio.class))).thenReturn(envioGuardado);

        EnvioResponse resultado = envioService.actualizarEnvio(5L, requestDto);

        assertNotNull(resultado);
        verify(envioRepo, times(1)).save(any(Envio.class));
    }

    @Test
    @DisplayName("actualizarEnvio() lanza EnvioNoEncontradoException cuando el envío no existe")
    void actualizarEnvio_DebeLanzarException_CuandoEnvioNoExiste() {
        when(envioRepo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EnvioNoEncontradoException.class, () ->
            envioService.actualizarEnvio(99L, requestDto)
        );
    }


    @Test
    @DisplayName("eliminarEnvio() elimina correctamente cuando existe")
    void eliminarEnvio_DebeEliminar_CuandoExiste() {
        when(envioRepo.findById(5L)).thenReturn(Optional.of(envioGuardado));
        doNothing().when(envioRepo).delete(envioGuardado);

        envioService.eliminarEnvio(5L);

        verify(envioRepo, times(1)).delete(envioGuardado);
    }

    @Test
    @DisplayName("eliminarEnvio() lanza EnvioNoEncontradoException cuando no existe")
    void eliminarEnvio_DebeLanzarException_CuandoNoExiste() {
        when(envioRepo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EnvioNoEncontradoException.class, () ->
            envioService.eliminarEnvio(99L)
        );
    }
}