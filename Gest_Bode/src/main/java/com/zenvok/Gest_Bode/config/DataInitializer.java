package com.zenvok.Gest_Bode.config;

import com.zenvok.Gest_Bode.model.Pasillo;
import com.zenvok.Gest_Bode.model.Estante;
import com.zenvok.Gest_Bode.model.Pas_Est;
import com.zenvok.Gest_Bode.repository.PasilloRepository;
import com.zenvok.Gest_Bode.repository.EstanteRepository;
import com.zenvok.Gest_Bode.repository.PasEstRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final PasilloRepository pasilloRepository;
    private final EstanteRepository estanteRepository;
    private final PasEstRepository pasEstRepository;

    @Override
    public void run(String... args) throws Exception {
        if (pasilloRepository.count() > 0) {
            log.info("Estructuras de bodega ya cargadas. Se omite la inicialización.");
            return;
        }

        log.info("Cargando datos iniciales de Gestión Bodega (Pasillos y Estantes)...");

        Pasillo pasilloA = new Pasillo();
        pasilloA.setNombrePasillo("Pasillo A - Envíos Rápidos");
        Pasillo pasilloAGuardado = pasilloRepository.save(pasilloA);

        Pasillo pasilloB = new Pasillo();
        pasilloB.setNombrePasillo("Pasillo B - Carga Pesada");
        pasilloRepository.save(pasilloB);

        Estante estante1 = new Estante();
        estante1.setNombreEstante("Estante Nivel 1 - Superior");
        Estante estante1Guardado = estanteRepository.save(estante1);

        Estante estante2 = new Estante();
        estante2.setNombreEstante("Estante Nivel 2 - Inferior");
        estanteRepository.save(estante2);

        Pas_Est relacionFisica = new Pas_Est();
        relacionFisica.setPasillo(pasilloAGuardado);
        relacionFisica.setEstante(estante1Guardado);
        pasEstRepository.save(relacionFisica);

        log.info("¡Estructuras de almacenamiento inicializadas con éxito en el Puerto 8085!");
    }
}
