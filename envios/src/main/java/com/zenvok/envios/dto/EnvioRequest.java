package com.zenvok.envios.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Modelo de petición para la creación o actualización de un envío")
public class EnvioRequest {

    @NotBlank(message = "La fecha de envio es obligatoria")
    @Schema(
        description = "Fecha en la que se realiza el envío (formato YYYY-MM-DD)",
        example = "2026-06-21",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String fechaEnvio;

    @NotBlank(message = "La fecha de embargue es obligatoria")
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

    @NotNull(message = "El id de la direccion es obligatorio")
    @Schema(
        description = "Identificador único de la dirección de destino",
        example = "105",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long direccionId;

    @NotNull(message = "El id de la venta es obligatorio")
    @Schema(
        description = "Identificador único de la venta asociada al envío",
        example = "5001",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long ventaId;

    @NotNull(message = "El id del estado es obligatorio")
    @Schema(
        description = "Identificador del estado actual del envío",
        example = "1",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long estadoId;
}