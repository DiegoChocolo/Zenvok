package com.zenvok.direccion.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Modelo de petición para la creación o actualización de una dirección")
public class DireccionRequestDTO {

    @NotBlank(message = "La calle es obligatoria")
    @Schema(
        description = "Nombre de la calle",
        example = "Arturo Prat",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String calle;

    @NotNull(message = "La numeración es obligatoria")
    @Schema(
        description = "Numeración numérica de la calle",
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

    @NotNull(message = "El id del estado es obligatorio")
    @Schema(
        description = "Identificador único del estado asociado",
        example = "1",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long estadoId;

    @NotNull(message = "El id de la comuna es obligatorio")
    @Schema(
        description = "Identificador único de la comuna asociada",
        example = "2",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long comunaId;

    @NotNull(message = "El id del usuario es obligatorio")
    @Schema(
        description = "Identificador único del usuario dueño de la dirección",
        example = "45",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long usuarioId;
}