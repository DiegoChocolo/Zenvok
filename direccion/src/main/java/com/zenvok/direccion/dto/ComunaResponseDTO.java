package com.zenvok.direccion.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Modelo de respuesta que contiene la información detallada de una comuna")
public class ComunaResponseDTO {
    @Schema(
        description = "El id de la comuna se genera de forma automatica",
        example = "1"      
    )
    private Long id;

    @Schema(
        description = "Nombre de la comuna",
        example = "Colina",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String nombreComuna;

}
