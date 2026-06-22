package com.zenvok.Ventas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BodegaProductoResponseDTO {

    private Long idProdEst;
    private Long productoId;
    private Long pasEstId;
    private String nombrePasillo;
    private String nombreEstante;
}
