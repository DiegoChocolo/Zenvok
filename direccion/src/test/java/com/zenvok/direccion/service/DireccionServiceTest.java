package com.zenvok.direccion.service;

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
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import com.zenvok.direccion.client.EstadoClient;
import com.zenvok.direccion.client.UsuarioClient;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT) 
@DisplayName("Test Unit de DireccionService")
public class DireccionServiceTest {

    @Mock
    private DireccionRepository direccionRepo;

    @Mock
    private ComunaRepository comunaRepo;

    @Mock
    private EstadoClient estadoClient;

    @Mock
    private UsuarioClient usuarioClient;

    @InjectMocks
    private DireccionService direccionService;

    private DireccionRequestDTO requestDto;
    private Direccion direccionGuardada;
    private Comuna comunaMock;
    private EstadoResponseDTO estadoMock;
    private UsuarioResponseDTO usuarioMock;

    @BeforeEach
    void setUp() {
        requestDto = new DireccionRequestDTO("Arturo Prat", 100, "Block A", 1L, 2L, 45L);

        comunaMock = new Comuna();
        comunaMock.setId(2L);
        comunaMock.setNombreComuna("SANTIAGO CENTRO");

        estadoMock = new EstadoResponseDTO(1L, "Pendiente", "Está por llegar o se encuentra en revisión");

        usuarioMock = new UsuarioResponseDTO(45L, "22573654-K", "Juan", "Gabriel", "Juan64@gmail.com", "Admin");

        direccionGuardada = new Direccion();
        direccionGuardada.setId(3L);
        direccionGuardada.setCalle("Arturo Prat");
        direccionGuardada.setNumeracion(100);
        direccionGuardada.setBlock("Block A");
        direccionGuardada.setEstadoId(1L);
        direccionGuardada.setComuna(comunaMock);
        direccionGuardada.setUsuarioId(45L);
    }

    @Test
    @DisplayName("crearDireccion() guarda exitosamente cuando todas las validaciones pasan (Camino Feliz)")
    void crearDireccion_DebeGuardar_CuandoComunaEstadoYUsuarioExisten() {
        when(comunaRepo.findById(2L)).thenReturn(Optional.of(comunaMock));
        when(estadoClient.buscarPorId(1L)).thenReturn(estadoMock);
        when(usuarioClient.buscarPorId(45L)).thenReturn(usuarioMock);
        when(direccionRepo.save(any(Direccion.class))).thenReturn(direccionGuardada);

        DireccionResponseDTO resultado = direccionService.crearDireccion(requestDto);

        assertNotNull(resultado);
        assertEquals(3L, resultado.getId());
        assertEquals("Arturo Prat", resultado.getCalle());
        assertEquals(100, resultado.getNumeracion());
        assertEquals(2L, resultado.getComunaId());
        assertEquals(45L, resultado.getUsuarioId());

        verify(direccionRepo, times(1)).save(any(Direccion.class));
    }

    @Test
    @DisplayName("crearDireccion() lanza ComunaReferenciaInvalidaException si la comuna no existe en la BD local")
    void crearDireccion_DebeLanzarException_CuandoComunaNoExiste() {
        when(estadoClient.buscarPorId(1L)).thenReturn(estadoMock);
        when(usuarioClient.buscarPorId(45L)).thenReturn(usuarioMock);
        when(comunaRepo.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ComunaReferenciaInvalidaException.class, () -> {
            direccionService.crearDireccion(requestDto);
        });
    }

    @Test
    @DisplayName("crearDireccion() lanza EstadoNoEncontradoException si EstadoClient devuelve vacío o falla")
    void crearDireccion_DebeLanzarException_CuandoEstadoNoExisteEnMicroservicio() {
        when(estadoClient.buscarPorId(1L)).thenReturn(null);

        EstadoNoEncontradoException excepcion = assertThrows(EstadoNoEncontradoException.class, () -> {
            direccionService.crearDireccion(requestDto);
        });

        assertEquals("Estado no encontrado con ID: 1", excepcion.getMessage());
    }

    @Test
    @DisplayName("crearDireccion() lanza UsuarioNoEncontradoException si UsuarioClient devuelve vacío o falla")
    void crearDireccion_DebeLanzarException_CuandoUsuarioNoExisteEnMicroservicio() {
        when(estadoClient.buscarPorId(1L)).thenReturn(estadoMock);
        when(usuarioClient.buscarPorId(45L)).thenReturn(null);

        UsuarioNoEncontradoException excepcion = assertThrows(UsuarioNoEncontradoException.class, () -> {
            direccionService.crearDireccion(requestDto);
        });

        assertEquals("Usuario no encontrado con ID: 45", excepcion.getMessage());
    }

    @Test
    @DisplayName("listarDirecciones() retorna la lista de todas las direcciones")
    void listarDirecciones_debeRetornarLista() {
        when(direccionRepo.findAll()).thenReturn(List.of(direccionGuardada));

        List<DireccionResponseDTO> resultado = direccionService.listarDirecciones();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    @DisplayName("obtenerPorId() retorna la dirección cuando existe")
    void obtenerPorId_debeRetornarDireccion_CuandoExiste() {
        when(direccionRepo.findById(3L)).thenReturn(Optional.of(direccionGuardada));

        DireccionResponseDTO resultado = direccionService.obtenerPorId(3L);

        assertNotNull(resultado);
        assertEquals(3L, resultado.getId());
    }

    @Test
    @DisplayName("obtenerPorId() lanza DireccionNoEncontradaException cuando no existe")
    void obtenerPorId_debeLanzarExcepcion_CuandoNoExiste() {
        when(direccionRepo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(DireccionNoEncontradaException.class, () -> {
            direccionService.obtenerPorId(99L);
        });
    }

    @Test
    @DisplayName("actualizarDireccion() actualiza correctamente cuando todo es válido")
    void actualizarDireccion_debeActualizar_CuandoTodoEsValido() {
        when(estadoClient.buscarPorId(1L)).thenReturn(estadoMock);
        when(usuarioClient.buscarPorId(45L)).thenReturn(usuarioMock);
        when(direccionRepo.findById(3L)).thenReturn(Optional.of(direccionGuardada));
        when(comunaRepo.findById(2L)).thenReturn(Optional.of(comunaMock));
        when(direccionRepo.save(any(Direccion.class))).thenReturn(direccionGuardada);

        DireccionResponseDTO resultado = direccionService.actualizarDireccion(3L, requestDto);

        assertNotNull(resultado);
        verify(direccionRepo, times(1)).save(any(Direccion.class));
    }

    @Test
    @DisplayName("actualizarDireccion() lanza DireccionNoEncontradaException cuando la dirección no existe")
    void actualizarDireccion_debeLanzarExcepcion_CuandoDireccionNoExiste() {
        when(direccionRepo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(DireccionNoEncontradaException.class, () -> {
            direccionService.actualizarDireccion(99L, requestDto);
        });
    }

    @Test
    @DisplayName("eliminarDireccion() elimina correctamente cuando existe")
    void eliminarDireccion_debeEliminar_CuandoExiste() {
        when(direccionRepo.findById(3L)).thenReturn(Optional.of(direccionGuardada));

        direccionService.eliminarDireccion(3L);

        verify(direccionRepo, times(1)).delete(direccionGuardada);
    }

    @Test
    @DisplayName("eliminarDireccion() lanza DireccionNoEncontradaException cuando no existe")
    void eliminarDireccion_debeLanzarExcepcion_CuandoNoExiste() {
        when(direccionRepo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(DireccionNoEncontradaException.class, () -> {
            direccionService.eliminarDireccion(99L);
        });
    }
}