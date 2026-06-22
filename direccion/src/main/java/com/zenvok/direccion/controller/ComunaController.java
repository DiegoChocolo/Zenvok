package com.zenvok.direccion.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zenvok.direccion.dto.ComunaRequestDTO;
import com.zenvok.direccion.dto.ComunaResponseDTO;
import com.zenvok.direccion.service.ComunaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/comunas")
@Tag(name = "comunas",description = "Controlador para gestionar las comunas de las direcion/es de usuarios")
@RequiredArgsConstructor
public class ComunaController {

    private final ComunaService comunaService;

    @GetMapping
    @Operation(summary = "Listar todas las comunas", description = "Devuelve una lista con todas las comunas ingresadas en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",description = "Lista de comunas obtenida con exito"),
        @ApiResponse(responseCode = "500",description = "Error interno del servidor"),
    }
    )
    public ResponseEntity<List<ComunaResponseDTO>> listarComunas() {
        return ResponseEntity.ok(comunaService.listarComunas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar comuna por su ID", description = "Devuelve una comuna buscada mediante su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",description = "Comuna obtenida con exito"),
        @ApiResponse(responseCode = "400",description = "ID ingresado con el formato incorrecto"),
        @ApiResponse(responseCode = "404",description = "Comuna no encontrada"),
        @ApiResponse(responseCode = "500",description = "Error interno del servidor"),
    }
    )
    public ResponseEntity<ComunaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(comunaService.obtenerPorId(id));
    }

    @GetMapping("/nombre/{nombreComuna}")
    @Operation(summary = "Buscar comuna por su nombre", description = "Devuelve una comuna buscada mediante su nombre")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",description = "Comuna obtenida con exito"),
        @ApiResponse(responseCode = "400",description = "ID ingresado en un formato invalido"),
        @ApiResponse(responseCode = "404",description = "Comuna no encontrada"),
        @ApiResponse(responseCode = "500",description = "Error interno del servidor"),

    }
    )
    public ResponseEntity<ComunaResponseDTO> buscarPorNombre(@PathVariable String nombreComuna) {
        return ResponseEntity.ok(comunaService.buscarPorNombre(nombreComuna));
    }

    @PostMapping
    @Operation(summary = "Añade una nueva comuna a la lista", description = "Crea una nueva comuna a partir de los datos ingresados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201",description = "Comuna creada con exito"),
        @ApiResponse(responseCode = "409",description = "Comuna ya existente"),
        @ApiResponse(responseCode = "422",description = "Comuna ingresada no cumple con la logica del negocio"),
        @ApiResponse(responseCode = "500",description = "Error interno del servidor"),
    }
    )
    public ResponseEntity<ComunaResponseDTO> crearComuna(@Valid @RequestBody ComunaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(comunaService.crearComuna(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza una comuna a la lista", description = "Actualiza una comuna buscandola por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",description = "Comuna actualizada con exito"),
        @ApiResponse(responseCode = "400",description = "ID ingresado en un formato invalido"),
        @ApiResponse(responseCode = "404",description = "Comuna ingresada no encontrada"),
        @ApiResponse(responseCode = "500",description = "Error interno del servidor"),
    }
    )
    public ResponseEntity<ComunaResponseDTO> actualizarComuna(
        @PathVariable Long id,
        @Valid @RequestBody ComunaRequestDTO dto
    ) {
        return ResponseEntity.ok(comunaService.actualizarComuna(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina una comuna a la lista", description = "Elimina una comuna buscandola por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204",description = "Comuna eliminada con exito"),
        @ApiResponse(responseCode = "400",description = "ID ingresado en un formato invalido"),
        @ApiResponse(responseCode = "404",description = "Comuna ingresada no encontrada"),
        @ApiResponse(responseCode = "409",description = "Comuna no eliminada contiene datos asociados a esta"),
        @ApiResponse(responseCode = "500",description = "Error interno del servidor"),
        @ApiResponse(responseCode = "503",description = "Error al comunicarse con otro microservicio")
    }
    )
    public ResponseEntity<Void> eliminarComuna(@PathVariable Long id) {
        comunaService.eliminarComuna(id);
        return ResponseEntity.noContent().build();
    }
}
