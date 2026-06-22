package com.zenvok.direccion.repository;

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

import com.zenvok.direccion.model.Comuna;
import com.zenvok.direccion.model.Direccion;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("Test de Integración de DireccionRepository")
class DireccionRepositoryTest {

    @Autowired
    private DireccionRepository direccionRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Comuna comuna;

    @BeforeEach
    void setUp() {
        comuna = new Comuna();
        comuna.setNombreComuna("SANTIAGO CENTRO");
        entityManager.persistAndFlush(comuna);

        Direccion direccion = new Direccion();
        direccion.setCalle("ARTURO PRAT");
        direccion.setNumeracion(100);
        direccion.setBlock("BLOCK A");
        direccion.setEstadoId(1L);
        direccion.setUsuarioId(45L);
        direccion.setComuna(comuna);
        entityManager.persistAndFlush(direccion);
    }

    @Test
    @DisplayName("findByUsuarioId() retorna las direcciones del usuario indicado")
    void findByUsuarioId_debeRetornarDirecciones_CuandoExisten() {
        List<Direccion> resultado = direccionRepository.findByUsuarioId(45L);

        assertTrue(resultado.size() >= 1);
        assertEquals(45L, resultado.get(0).getUsuarioId());
    }

    @Test
    @DisplayName("findByUsuarioId() retorna lista vacía si el usuario no tiene direcciones")
    void findByUsuarioId_debeRetornarVacio_CuandoNoExisten() {
        List<Direccion> resultado = direccionRepository.findByUsuarioId(999L);

        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("findByComuna_Id() retorna las direcciones asociadas a una comuna")
    void findByComunaId_debeRetornarDirecciones_CuandoExisten() {
        List<Direccion> resultado = direccionRepository.findByComuna_Id(comuna.getId());

        assertTrue(resultado.size() >= 1);
        assertEquals(comuna.getId(), resultado.get(0).getComuna().getId());
    }

    @Test
    @DisplayName("findByEstadoId() retorna las direcciones con el estado indicado")
    void findByEstadoId_debeRetornarDirecciones_CuandoExisten() {
        List<Direccion> resultado = direccionRepository.findByEstadoId(1L);

        assertTrue(resultado.size() >= 1);
        assertEquals(1L, resultado.get(0).getEstadoId());
    }
}