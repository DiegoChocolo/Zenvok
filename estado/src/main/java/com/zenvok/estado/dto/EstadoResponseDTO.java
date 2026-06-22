package com.zenvok.estado.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstadoResponseDTO {

    private Long id;
    private String nombre;
    private String descripcion;
}