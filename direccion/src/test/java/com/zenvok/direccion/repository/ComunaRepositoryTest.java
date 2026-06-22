package com.zenvok.direccion.repository;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import com.zenvok.direccion.model.Comuna;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("Test de Integración de ComunaRepository")
class ComunaRepositoryTest {

    @Autowired
    private ComunaRepository comunaRepository;

    @Test
    @DisplayName("findByNombreComunaIgnoreCase() encuentra la comuna sin importar mayúsculas/minúsculas")
    void findByNombreComunaIgnoreCase_debeRetornarComuna_CuandoExiste() {

        Comuna comuna = new Comuna();
        comuna.setNombreComuna("SANTIAGO CENTRO");
        comunaRepository.save(comuna);

        Optional<Comuna> resultado = comunaRepository.findByNombreComunaIgnoreCase("sAnTiAgO cEnTrO");

        assertTrue(resultado.isPresent());
        assertNotNull(resultado.get().getId());
        assertTrue(resultado.get().getNombreComuna().equalsIgnoreCase("SANTIAGO CENTRO"));
    }

    @Test
    @DisplayName("findByNombreComunaIgnoreCase() retorna vacío si la comuna no existe")
    void findByNombreComunaIgnoreCase_debeRetornarVacio_CuandoNoExiste() {
    
        Optional<Comuna> resultado = comunaRepository.findByNombreComunaIgnoreCase("PROVIDENCIA");

        assertFalse(resultado.isPresent());
    }
}
