package com.zenvok.Gest_Bode.controller;

import com.zenvok.Gest_Bode.dto.PasEstRequestDTO;
import com.zenvok.Gest_Bode.dto.PasEstResponseDTO;
import com.zenvok.Gest_Bode.service.PasEstService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bodega_estructuras")
@RequiredArgsConstructor
public class PasEstController {

    private final PasEstService pasEstService;

    @PostMapping
    public ResponseEntity<PasEstResponseDTO> asignarEstanteAPasillo(@Valid @RequestBody PasEstRequestDTO request) {
        return new ResponseEntity<>(pasEstService.guardar(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PasEstResponseDTO>> listarTodas() {
        return ResponseEntity.ok(pasEstService.obtenerTodos());
    }

    @GetMapping("/pasillo/{idPasillo}")
    public ResponseEntity<List<PasEstResponseDTO>> listarPorPasillo(@PathVariable Long idPasillo) {
        return ResponseEntity.ok(pasEstService.listarEstantesPorPasillo(idPasillo));
    }
}
