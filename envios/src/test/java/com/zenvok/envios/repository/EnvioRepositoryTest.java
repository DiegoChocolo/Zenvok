package com.zenvok.envios.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import com.zenvok.envios.model.Envio;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("Test de Integración de EnvioRepository")
class EnvioRepositoryTest {

    @Autowired
    private EnvioRepository envioRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        Envio envio = new Envio(null, "2026-06-21", "2026-06-22", "Test", 1L, 10L, 2L);
        entityManager.persistAndFlush(envio);
    }

    @Test
    @DisplayName("findByVentaId() retorna los envíos asociados a una venta")
    void findByVentaId_DebeRetornarEnvios_CuandoExisten() {
        List<Envio> resultado = envioRepository.findByVentaId(10L);

        assertTrue(resultado.size() >= 1);
        assertEquals(10L, resultado.get(0).getVentaId());
    }

    @Test
    @DisplayName("findByVentaId() retorna lista vacía si no hay envíos para esa venta")
    void findByVentaId_DebeRetornarVacio_CuandoNoExisten() {
        List<Envio> resultado = envioRepository.findByVentaId(999L);

        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("findByDireccionId() retorna los envíos con la dirección indicada")
    void findByDireccionId_DebeRetornarEnvios_CuandoExisten() {
        List<Envio> resultado = envioRepository.findByDireccionId(1L);

        assertTrue(resultado.size() >= 1);
        assertEquals(1L, resultado.get(0).getDireccionId());
    }

    @Test
    @DisplayName("findByEstadoId() retorna los envíos con el estado indicado")
    void findByEstadoId_DebeRetornarEnvios_CuandoExisten() {
        List<Envio> resultado = envioRepository.findByEstadoId(2L);

        assertTrue(resultado.size() >= 1);
        assertEquals(2L, resultado.get(0).getEstadoId());
    }
}