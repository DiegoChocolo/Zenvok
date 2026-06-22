package com.zenvok.envios.exception;

public class EnvioNoEncontradoException extends RuntimeException {
    public EnvioNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}