package com.zenvok.envios.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Modelo de respuesta que contiene la información detallada de un envío")
public class EnvioResponse {

    @Schema(
        description = "Identificador único del envío en la base de datos",
        example = "25",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long id;

    @Schema(
        description = "Fecha en la que se realizó el envío (formato YYYY-MM-DD)",
        example = "2026-06-21",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String fechaEnvio;

    @Schema(
        description = "Fecha estimada o real de embarque (formato YYYY-MM-DD)",
        example = "2026-06-22",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String fechaEmbargue;

    @Schema(
        description = "Comentarios o notas adicionales sobre el envío",
        example = "Entregar en portería si no hay nadie",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String comentarios;

    @Schema(
        description = "Identificador único de la dirección de destino",
        example = "105",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long direccionId;

    @Schema(
        description = "Identificador único de la venta asociada al envío",
        example = "5001",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long ventaId;

    @Schema(
        description = "Identificador del estado actual del envío",
        example = "1",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long estadoId;
}