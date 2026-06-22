package com.zenvok.Gest_Bode.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdPasEstRequestDTO {

    @NotNull(message = "El ID de la relación Pasillo-Estante (pasEst) es obligatorio")
    private Long pasEstId;

    @NotNull(message = "El ID del producto es obligatorio")
    private Long productoId;
}
