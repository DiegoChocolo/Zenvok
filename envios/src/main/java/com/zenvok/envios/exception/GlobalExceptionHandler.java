package com.zenvok.envios.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.zenvok.envios.dto.ErrorResponseDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EnvioNoEncontradoException.class)
    public ResponseEntity<ErrorResponseDTO> handleEnvioNoEncontrado(EnvioNoEncontradoException ex, WebRequest req) {
        log.warn("Envío no encontrado: {}", ex.getMessage());
        return construirRespuesta(HttpStatus.NOT_FOUND, ex.getMessage(), req);
    }

    @ExceptionHandler(DireccionNoDisponibleException.class)
    public ResponseEntity<ErrorResponseDTO> handleDireccionNoDisponible(DireccionNoDisponibleException ex, WebRequest req) {
        log.warn("MS Dirección no disponible: {}", ex.getMessage());
        return construirRespuesta(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage(), req);
    }

    @ExceptionHandler(VentaNoDisponibleException.class)
    public ResponseEntity<ErrorResponseDTO> handleVentaNoDisponible(VentaNoDisponibleException ex, WebRequest req) {
        log.warn("MS Venta no disponible: {}", ex.getMessage());
        return construirRespuesta(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage(), req);
    }

    @ExceptionHandler(EstadoNoDisponibleException.class)
    public ResponseEntity<ErrorResponseDTO> handleEstadoNoDisponible(EstadoNoDisponibleException ex, WebRequest req) {
        log.warn("MS Estado no disponible: {}", ex.getMessage());
        return construirRespuesta(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage(), req);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidacion(MethodArgumentNotValidException ex, WebRequest req) {
        Map<String, String> errores = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errores.put(error.getField(), error.getDefaultMessage());
        }

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Datos inválidos");
        body.put("errores", errores);
        body.put("path", req.getDescription(false).replace("uri=", ""));

        log.warn("Error de validación: {}", errores);
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDTO> handleTipoInvalido(MethodArgumentTypeMismatchException ex, WebRequest req) {
        String mensaje = "El parámetro '" + ex.getName() + "' debe ser de tipo " + ex.getRequiredType().getSimpleName();
        log.warn("Tipo de parámetro inválido: {}", mensaje);
        return construirRespuesta(HttpStatus.BAD_REQUEST, mensaje, req);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGeneral(Exception ex, WebRequest req) {
        log.error("Error interno no controlado: ", ex);
        return construirRespuesta(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error interno en el servidor", req);
    }

    private ResponseEntity<ErrorResponseDTO> construirRespuesta(HttpStatus status, String mensaje, WebRequest req) {
        ErrorResponseDTO error = new ErrorResponseDTO(
            LocalDateTime.now(),
            status.value(),
            status.getReasonPhrase(),
            mensaje,
            req.getDescription(false).replace("uri=", "")
        );
        return ResponseEntity.status(status).body(error);
    }
}