package com.zenvok.direccion.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Modelo de petición para la creación o actualización de una comuna")
public class ComunaRequestDTO {
    @NotBlank(message = "El nombre no puede estar vacio")
    @Size(min = 2,max = 100)
    @Schema(
        description = "Nombre de la comuna",
        example = "Colina",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String nombreComuna;

}
