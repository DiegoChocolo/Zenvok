package com.zenvok.envios.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Modelo de respuesta que contiene la información detallada de una dirección")
public class DireccionResponse {

    @Schema(
        description = "El id único de la dirección generado automáticamente",
        example = "3",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long id;

    @Schema(
        description = "Nombre de la calle",
        example = "Arturo Prat",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String calle;

    @Schema(
        description = "Numeración de la calle anteriormente indicada",
        example = "100",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Integer numeracion;

    @Schema(
        description = "Detalles adicionales de la vivienda (Block, departamento, torre, etc.)",
        example = "Block A, Depto 201",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String block;

    @Schema(
        description = "Identificador único del estado asociado",
        example = "1",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long estadoId;

    @Schema(
        description = "Identificador único de la comuna asociada",
        example = "2",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long comunaId;

    @Schema(
        description = "Identificador único del usuario dueño de la dirección",
        example = "45",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long usuarioId;
}