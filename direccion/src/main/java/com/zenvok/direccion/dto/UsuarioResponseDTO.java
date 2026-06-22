package com.zenvok.direccion.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Modelo de respuesta que contiene la información detallada de un usuario")
public class UsuarioResponseDTO {

    @Schema(
        description = "El id de la comuna se genera de forma automatica",
        example = "5",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long id;
    
    @Schema(
        description = "Rut del usuario este ya debe de estar ingresado",
        example = "22573654",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String rut;
    @Schema(
        description = "Nombre del usuario este ya debe de estar ingresado",
        example = "Juan",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String nombre;

    @Schema(
        description = "Apellido del usuario este ya debe de estar ingresado",
        example = "Gabriel",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String apellido;

    @Schema(
        description = "Correo del usuario este ya debe de estar ingresado",
        example = "Juan64@gmail.com",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String correo;

    @Schema(
        description = "Nombre de rol del usuario este ya debe de estar ingresado",
        example = "Admin",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String nombreRol;
}