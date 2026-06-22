package com.zenvok.Ventas.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VentaRequestDTO {

    @NotNull(message = "La fecha de venta es obligatoria")
    private LocalDate fechaVentas;

    @NotNull(message = "El total no puede estar vacío")
    @DecimalMin(value = "1", message = "El total debe ser mayor a 0")
    private BigDecimal total;

    @NotNull(message = "El ID del usuario es obligatorio")
    private Long usuarioId;

    @NotNull(message = "El ID del estado es obligatorio")
    private Long estadoId;
}
