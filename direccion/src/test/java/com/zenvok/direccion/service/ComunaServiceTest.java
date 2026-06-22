package com.zenvok.direccion.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.zenvok.direccion.dto.ComunaRequestDTO;
import com.zenvok.direccion.dto.ComunaResponseDTO;
import com.zenvok.direccion.exception.ComunaDuplicadaException;
import com.zenvok.direccion.exception.ComunaNoEncontradaException;
import com.zenvok.direccion.model.Comuna;
import com.zenvok.direccion.repository.ComunaRepository;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT) // Evita fallos por stubs no usados en flujos alternativos
@DisplayName("Test Unit de ComunaService")
public class ComunaServiceTest {

    @Mock
    private ComunaRepository comunaRepo;

    @InjectMocks
    private ComunaService comunaService;

    private Comuna comunaEjemplo;
    private ComunaRequestDTO requestDTO;

    @BeforeEach
    void setUp(){
        comunaEjemplo = new Comuna();
        comunaEjemplo.setId(1L);
        comunaEjemplo.setNombreComuna("SANTIAGO");

        requestDTO = new ComunaRequestDTO("santiago");
    }

    @Test
    @DisplayName("listarComunas() retorna la lista de ResponseDTO de todas las comunas")
    void listarComunas_debeRetornarListaDeComuna(){
        when(comunaRepo.findAll()).thenReturn(List.of());

        List<ComunaResponseDTO> resultado = comunaService.listarComunas();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("crearComuna() guarda exitosamente y retorna un ComunaResponseDTO mapeado")
    void crearComuna_debeGuardarYRetornarResponseDTO_CuandoNoExisteDuplicado(){
        when(comunaRepo.findByNombreComunaIgnoreCase(requestDTO.getNombreComuna())).thenReturn(Optional.empty());
        when(comunaRepo.save(any(Comuna.class))).thenReturn(comunaEjemplo);

        ComunaResponseDTO resultado = comunaService.crearComuna(requestDTO);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("SANTIAGO", resultado.getNombreComuna());
        verify(comunaRepo, times(1)).save(any(Comuna.class));
    }

    @Test
    @DisplayName("crearComuna() lanza ComunaDuplicadaException si el nombre de la comuna ya existe")
    void crearComuna_debeLanzarException_SiComunaYaExiste() {
        when(comunaRepo.findByNombreComunaIgnoreCase(requestDTO.getNombreComuna())).thenReturn(Optional.of(comunaEjemplo));

        ComunaDuplicadaException excepcion = assertThrows(ComunaDuplicadaException.class, () -> {
            comunaService.crearComuna(requestDTO);
        });

        assertEquals("Ya existe una comuna con el nombre: " + requestDTO.getNombreComuna(), excepcion.getMessage());
    }

    @Test
    @DisplayName("obtenerPorId() retorna el ResponseDTO cuando la comuna existe")
    void obtenerPorId_debeRetornarComuna_CuandoExiste() {
        when(comunaRepo.findById(1L)).thenReturn(Optional.of(comunaEjemplo));

        ComunaResponseDTO resultado = comunaService.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals("SANTIAGO", resultado.getNombreComuna());
    }

    @Test
    @DisplayName("obtenerPorId() lanza ComunaNoEncontradaException cuando la comuna no existe")
    void obtenerPorId_debeLanzarExcepcion_CuandoNoExiste() {
        when(comunaRepo.findById(99L)).thenReturn(Optional.empty());

        ComunaNoEncontradaException excepcion = assertThrows(ComunaNoEncontradaException.class, () -> {
            comunaService.obtenerPorId(99L);
        });

        assertEquals("Comuna no encontrada con id: 99", excepcion.getMessage());
    }

    @Test
    @DisplayName("buscarPorNombre() retorna el ResponseDTO cuando la comuna existe")
    void buscarPorNombre_debeRetornarComuna_CuandoExiste() {
        when(comunaRepo.findByNombreComunaIgnoreCase("santiago")).thenReturn(Optional.of(comunaEjemplo));

        ComunaResponseDTO resultado = comunaService.buscarPorNombre("santiago");

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    @DisplayName("buscarPorNombre() lanza ComunaNoEncontradaException cuando la comuna no existe")
    void buscarPorNombre_debeLanzarExcepcion_CuandoNoExiste() {
        when(comunaRepo.findByNombreComunaIgnoreCase("providencia")).thenReturn(Optional.empty());

        assertThrows(ComunaNoEncontradaException.class, () -> {
            comunaService.buscarPorNombre("providencia");
        });
    }

    @Test
    @DisplayName("actualizarComuna() actualiza correctamente cuando el nuevo nombre está libre")
    void actualizarComuna_debeActualizar_CuandoNombreEstaLibre() {
        ComunaRequestDTO nuevoDto = new ComunaRequestDTO("providencia");
        

        Comuna comunaActualizada = new Comuna();
        comunaActualizada.setId(1L);
        comunaActualizada.setNombreComuna("PROVIDENCIA");

        when(comunaRepo.findById(1L)).thenReturn(Optional.of(comunaEjemplo));
        when(comunaRepo.findByNombreComunaIgnoreCase("providencia")).thenReturn(Optional.empty());
        when(comunaRepo.save(any(Comuna.class))).thenReturn(comunaActualizada); // Retornamos la actualizada

        ComunaResponseDTO resultado = comunaService.actualizarComuna(1L, nuevoDto);

        assertNotNull(resultado);
        assertEquals("PROVIDENCIA", resultado.getNombreComuna()); 
        verify(comunaRepo, times(1)).save(any(Comuna.class));
    }

    @Test
    @DisplayName("actualizarComuna() lanza ComunaNoEncontradaException cuando el id no existe")
    void actualizarComuna_debeLanzarExcepcion_CuandoIdNoExiste() {
        when(comunaRepo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ComunaNoEncontradaException.class, () -> {
            comunaService.actualizarComuna(99L, requestDTO);
        });
    }

    @Test
    @DisplayName("actualizarComuna() lanza ComunaDuplicadaException si el nuevo nombre ya lo tiene otra comuna")
    void actualizarComuna_debeLanzarExcepcion_CuandoNombreOcupadoPorOtraComuna() {
        Comuna otraComuna = new Comuna();
        otraComuna.setId(2L);
        otraComuna.setNombreComuna("PROVIDENCIA");

        ComunaRequestDTO dto = new ComunaRequestDTO("providencia");

        when(comunaRepo.findById(1L)).thenReturn(Optional.of(comunaEjemplo));
        when(comunaRepo.findByNombreComunaIgnoreCase("providencia")).thenReturn(Optional.of(otraComuna));

        assertThrows(ComunaDuplicadaException.class, () -> {
            comunaService.actualizarComuna(1L, dto);
        });
    }

    @Test
    @DisplayName("eliminarComuna() elimina correctamente cuando la comuna existe")
    void eliminarComuna_debeEliminar_CuandoExiste() {
        when(comunaRepo.findById(1L)).thenReturn(Optional.of(comunaEjemplo));

        comunaService.eliminarComuna(1L);

        verify(comunaRepo, times(1)).delete(comunaEjemplo);
    }

    @Test
    @DisplayName("eliminarComuna() lanza ComunaNoEncontradaException cuando la comuna no existe")
    void eliminarComuna_debeLanzarExcepcion_CuandoNoExiste() {
        when(comunaRepo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ComunaNoEncontradaException.class, () -> {
            comunaService.eliminarComuna(99L);
        });
    }
}