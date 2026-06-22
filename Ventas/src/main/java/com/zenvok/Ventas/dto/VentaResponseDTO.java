package com.zenvok.Ventas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VentaResponseDTO {

    private Long id;
    private LocalDate fechaVentas;
    private BigDecimal total;

    private Long usuarioId;
    private String usuarioNombre;

    private Long estadoId;
    private String estadoNombre;
}