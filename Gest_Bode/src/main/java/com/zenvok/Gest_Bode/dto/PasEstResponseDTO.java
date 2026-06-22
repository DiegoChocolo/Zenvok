package com.zenvok.Gest_Bode.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasEstResponseDTO {
    private Long idPasEst;
    
    private Long pasilloId;
    private String nombrePasillo;
    
    private Long estanteId;
    private String nombreEstante;
}