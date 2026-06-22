package com.zenvok.direccion.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.zenvok.direccion.dto.DireccionRequestDTO;
import com.zenvok.direccion.dto.DireccionResponseDTO;
import com.zenvok.direccion.service.DireccionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/direcciones")
@Tag(name = "direcciones", description = "Controlador para gestionar las direcciones físicas de los usuarios")
@RequiredArgsConstructor
public class DireccionController {

    private final DireccionService direccionService;

    @GetMapping
    @Operation(summary = "Listar todas las direcciones", description = "Devuelve una lista completa con todas las direcciones registradas en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de direcciones obtenida con éxito"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor"),
        @ApiResponse(responseCode = "503", description = "Error al comunicarse con el microservicio externo")

    })
    public ResponseEntity<List<DireccionResponseDTO>> listarDirecciones() {
        return ResponseEntity.ok(direccionService.listarDirecciones());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar dirección por su ID", description = "Devuelve los detalles de una dirección específica mediante su ID único")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Dirección obtenida con éxito"),
        @ApiResponse(responseCode = "400", description = "ID ingresado con el formato incorrecto"),
        @ApiResponse(responseCode = "404", description = "Dirección no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor"),
        @ApiResponse(responseCode = "503", description = "Error al comunicarse con el microservicio externo")

    })
    public ResponseEntity<DireccionResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(direccionService.obtenerPorId(id));
    }

    @PostMapping
    @Operation(summary = "Crear una nueva dirección", description = "Registra una nueva dirección asociada a un usuario y a una comuna válida")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Dirección creada con éxito"),
        @ApiResponse(responseCode = "400", description = "Datos del JSON inválidos o faltantes"),
        @ApiResponse(responseCode = "422", description = "La comuna o el usuario proporcionado no existen en el sistema"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor"),
        @ApiResponse(responseCode = "503", description = "Error al comunicarse con el microservicio externo")

    })
    public ResponseEntity<DireccionResponseDTO> crearDireccion(@Valid @RequestBody DireccionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(direccionService.crearDireccion(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una dirección existente", description = "Modifica los datos de una dirección buscando por su ID único")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Dirección actualizada con éxito"),
        @ApiResponse(responseCode = "400", description = "ID o datos del JSON en formato inválido"),
        @ApiResponse(responseCode = "404", description = "Dirección no encontrada"),
        @ApiResponse(responseCode = "422", description = "Los nuevos datos de comuna o usuario no son válidos"),
        @ApiResponse(responseCode = "503", description = "Error al comunicarse con el microservicio externo"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<DireccionResponseDTO> actualizarDireccion(
        @PathVariable Long id,
        @Valid @RequestBody DireccionRequestDTO dto
    ) {
        return ResponseEntity.ok(direccionService.actualizarDireccion(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una dirección", description = "Elimina permanentemente una dirección del sistema mediante su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Dirección eliminada con éxito"),
        @ApiResponse(responseCode = "400", description = "ID ingresado en un formato inválido"),
        @ApiResponse(responseCode = "404", description = "Dirección que se intenta eliminar no existe"),
        @ApiResponse(responseCode = "409", description = "No se puede eliminar la dirección porque contiene datos o dependencias asociadas en otras entidades"),
        @ApiResponse(responseCode = "503", description = "Error al comunicarse con el microservicio externo"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> eliminarDireccion(@PathVariable Long id) {
        direccionService.eliminarDireccion(id);
        return ResponseEntity.noContent().build();

    }
}