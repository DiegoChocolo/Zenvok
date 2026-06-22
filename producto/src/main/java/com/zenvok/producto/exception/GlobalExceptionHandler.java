package com.zenvok.producto.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.zenvok.producto.dto.ErrorResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidateErrors(
            MethodArgumentNotValidException ex
    ) {
        Map<String, String> errores = new LinkedHashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errores.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(errores);
    }

    @ExceptionHandler({
        ProductoNotFoundException.class,
        CategoriaNotFoundException.class
    })
    public ResponseEntity<ErrorResponseDTO> handleNotFound(RuntimeException ex){
        ErrorResponseDTO error = new ErrorResponseDTO(
            "RECURSO_NO_ENCONTRADO",
            ex.getMessage(),
            LocalDateTime.now()
        );
        return ResponseEntity.status(404).body(error);
    }

    @ExceptionHandler(RuntimeException .class)
    public ResponseEntity<ErrorResponseDTO> handleRuntimeException(RuntimeException ex){
        ErrorResponseDTO error = new ErrorResponseDTO(
            "ERROR_NEGOCIO",
            ex.getMessage(),
            LocalDateTime.now()
        );
        return ResponseEntity.badRequest().body(error);
    }
}
