package com.zenvok.direccion.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.zenvok.direccion.model.Comuna;
import com.zenvok.direccion.model.Direccion;
import com.zenvok.direccion.repository.ComunaRepository;
import com.zenvok.direccion.repository.DireccionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final DireccionRepository direccionRepository;
    private final ComunaRepository comunaRepository;

    @Override
    public void run(String... args) {
        if (comunaRepository.count() > 0) {
            log.info("Datos ya cargados, se omite la inicialización");
            return;
        }

        log.info("Cargando comunas...");

        Comuna santiago = new Comuna();
        santiago.setNombreComuna("Santiago");

        Comuna providencia = new Comuna();
        providencia.setNombreComuna("Providencia");

        Comuna lasCondes = new Comuna();
        lasCondes.setNombreComuna("Las Condes");

        santiago = comunaRepository.save(santiago);
        providencia = comunaRepository.save(providencia);
        lasCondes = comunaRepository.save(lasCondes);

        log.info("Comunas cargadas exitosamente");

        log.info("Cargando direcciones...");

        Direccion d1 = new Direccion();
        d1.setCalle("Av. Providencia");
        d1.setNumeracion(1234);
        d1.setBlock("Torre A");
        d1.setEstadoId(1L);
        d1.setUsuarioId(1L);
        d1.setComuna(providencia); 

        Direccion d2 = new Direccion();
        d2.setCalle("Alameda");
        d2.setNumeracion(4321);
        d2.setBlock("Sin block");
        d2.setEstadoId(1L);
        d2.setUsuarioId(2L);
        d2.setComuna(santiago); 

        Direccion d3 = new Direccion();
        d3.setCalle("Av. Apoquindo");
        d3.setNumeracion(555);
        d3.setBlock("Torre B");
        d3.setEstadoId(2L);
        d3.setUsuarioId(3L);
        d3.setComuna(lasCondes); 

        direccionRepository.save(d1);
        direccionRepository.save(d2);
        direccionRepository.save(d3);

        log.info("Direcciones cargadas exitosamente");
    }
}