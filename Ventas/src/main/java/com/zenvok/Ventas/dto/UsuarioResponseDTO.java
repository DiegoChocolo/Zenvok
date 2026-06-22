package com.zenvok.Ventas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponseDTO {

    private Long id;
    private String rut;
    private String nombre;
    private String apellido;
    private String correo;
    private String nombreRol;
}