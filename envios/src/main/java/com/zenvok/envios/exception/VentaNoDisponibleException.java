package com.zenvok.envios.exception;

public class VentaNoDisponibleException extends RuntimeException {
    public VentaNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}