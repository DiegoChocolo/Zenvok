package com.zenvok.envios.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.zenvok.envios.model.Envio;
import com.zenvok.envios.repository.EnvioRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final EnvioRepository envioRepository;

    @Override
    public void run(String... args) {
        if (envioRepository.count() > 0) {
            log.info("Envios ya cargados, se omite la inicializacion");
            return;
        }

        log.info("Cargando envios iniciales...");

        Envio e1 = new Envio();
        e1.setFechaEnvio("2024-01-15");
        e1.setFechaEmbargue("2024-01-20");
        e1.setComentarios("Envio express");
        e1.setDireccionId(1L);
        e1.setVentaId(1L);
        e1.setEstadoId(1L);

        Envio e2 = new Envio();
        e2.setFechaEnvio("2024-01-16");
        e2.setFechaEmbargue("2024-01-22");
        e2.setComentarios("Envio normal");
        e2.setDireccionId(2L);
        e2.setVentaId(2L);
        e2.setEstadoId(2L);

        Envio e3 = new Envio();
        e3.setFechaEnvio("2024-01-17");
        e3.setFechaEmbargue("2024-01-25");
        e3.setComentarios("Fragil, manejar con cuidado");
        e3.setDireccionId(3L);
        e3.setVentaId(3L);
        e3.setEstadoId(1L);

        envioRepository.save(e1);
        envioRepository.save(e2);
        envioRepository.save(e3);

        log.info("Envios cargados exitosamente");
    }
}
