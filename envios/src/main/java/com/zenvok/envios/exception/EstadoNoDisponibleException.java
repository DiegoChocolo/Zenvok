package com.zenvok.envios.exception;

public class EstadoNoDisponibleException extends RuntimeException {
    public EstadoNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}