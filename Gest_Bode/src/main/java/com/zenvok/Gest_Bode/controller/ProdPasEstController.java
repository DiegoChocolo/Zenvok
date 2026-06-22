package com.zenvok.Gest_Bode.controller;

import com.zenvok.Gest_Bode.dto.ProdPasEstRequestDTO;
import com.zenvok.Gest_Bode.dto.ProdPasEstResponseDTO;
import com.zenvok.Gest_Bode.service.ProdPasEstService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos_ubicaciones")
@RequiredArgsConstructor
public class ProdPasEstController {

    private final ProdPasEstService prodPasEstService;

    @PostMapping
    public ResponseEntity<ProdPasEstResponseDTO> ubicarProducto(@Valid @RequestBody ProdPasEstRequestDTO request) {
        return new ResponseEntity<>(prodPasEstService.guardar(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProdPasEstResponseDTO>> listarTodas() {
        return ResponseEntity.ok(prodPasEstService.obtenerTodos());
    }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<ProdPasEstResponseDTO> buscarPorProducto(@PathVariable Long productoId) {
        return prodPasEstService.buscarPorProductoId(productoId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
