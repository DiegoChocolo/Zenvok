package com.zenvok.gestionusuario.dto;

import lombok.Data;

@Data

public class UsuarioResponseDTO {
    
    private Long id;
    private String rut;
    private String nombre;
    private String apellido;
    private String correo;
    private String nombreRol;

}
