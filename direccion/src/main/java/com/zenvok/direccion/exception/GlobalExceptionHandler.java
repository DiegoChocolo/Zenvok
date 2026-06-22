package com.zenvok.direccion.exception;

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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ── 404 — recurso buscado directamente, no existe ──
    @ExceptionHandler(ComunaNoEncontradaException.class)
    public ResponseEntity<ErrorResponseDTO> handleComunaNoEncontrada(ComunaNoEncontradaException ex, WebRequest req) {
        log.warn("Comuna no encontrada: {}", ex.getMessage());
        return construirRespuesta(HttpStatus.NOT_FOUND, ex.getMessage(), req);
    }

    @ExceptionHandler(DireccionNoEncontradaException.class)
    public ResponseEntity<ErrorResponseDTO> handleDireccionNoEncontrada(DireccionNoEncontradaException ex, WebRequest req) {
        log.warn("Dirección no encontrada: {}", ex.getMessage());
        return construirRespuesta(HttpStatus.NOT_FOUND, ex.getMessage(), req);
    }

    // ── 409 — conflicto, ya existe un recurso igual ──
    @ExceptionHandler(ComunaDuplicadaException.class)
    public ResponseEntity<ErrorResponseDTO> handleComunaDuplicada(ComunaDuplicadaException ex, WebRequest req) {
        log.warn("Comuna duplicada: {}", ex.getMessage());
        return construirRespuesta(HttpStatus.CONFLICT, ex.getMessage(), req);
    }

    // ── 422 — el JSON está bien formado pero referencia un ID inexistente ──
    @ExceptionHandler(ComunaReferenciaInvalidaException.class)
    public ResponseEntity<ErrorResponseDTO> handleComunaReferenciaInvalida(ComunaReferenciaInvalidaException ex, WebRequest req) {
        log.warn("Referencia de comuna inválida: {}", ex.getMessage());
        return construirRespuesta(HttpStatus.UNPROCESSABLE_CONTENT, ex.getMessage(), req);
    }

    @ExceptionHandler(EstadoNoEncontradoException.class)
    public ResponseEntity<ErrorResponseDTO> handleEstadoNoEncontrado(EstadoNoEncontradoException ex, WebRequest req) {
        log.warn("Estado no encontrado en microservicio externo: {}", ex.getMessage());
        return construirRespuesta(HttpStatus.UNPROCESSABLE_CONTENT, ex.getMessage(), req);
    }

    @ExceptionHandler(UsuarioNoEncontradoException.class)
    public ResponseEntity<ErrorResponseDTO> handleUsuarioNoEncontrado(UsuarioNoEncontradoException ex, WebRequest req) {
        log.warn("Usuario no encontrado en microservicio externo: {}", ex.getMessage());
        return construirRespuesta(HttpStatus.UNPROCESSABLE_CONTENT, ex.getMessage(), req);
    }

    // ── 400 — falló @NotBlank/@NotNull del RequestDTO ──
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

    // ── 400 — el ID en la URL no es del tipo esperado, ej: /api/comunas/abc ──
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDTO> handleTipoInvalido(MethodArgumentTypeMismatchException ex, WebRequest req) {
        String mensaje = "El parámetro '" + ex.getName() + "' debe ser de tipo " + ex.getRequiredType().getSimpleName();
        log.warn("Tipo de parámetro inválido: {}", mensaje);
        return construirRespuesta(HttpStatus.BAD_REQUEST, mensaje, req);
    }

    // ── 500 — red de seguridad para cualquier error no controlado ──
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
