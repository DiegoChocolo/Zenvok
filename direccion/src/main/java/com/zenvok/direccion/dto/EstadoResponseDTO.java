package com.zenvok.direccion.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Modelo de respuesta que contiene la información detallada de un estado")
public class EstadoResponseDTO {

    @Schema(
        description = "Id del estado se genera de forma automatica",
        example = "55",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long id;
    @Schema(
        description = "Nombre del estado",
        example = "Pendiente",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String nombre;
    @Schema(
        description = "Descripcion del estado",
        example = "Esta por llegar o se encuentra en revision",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String descripcion;
}
