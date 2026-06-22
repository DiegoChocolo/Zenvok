package com.zenvok.Gest_Bode.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasEstRequestDTO {

    @NotNull(message = "El ID del pasillo es obligatorio")
    private Long pasilloId;

    @NotNull(message = "El ID del estante es obligatorio")
    private Long estanteId;
}
