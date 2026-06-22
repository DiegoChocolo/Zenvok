package com.zenvok.Ventas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Detalle")
@Schema(description = "Entidad que representa la boleta de Ventas.")
public class Detalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Detalle")
    private Long id;

    @NotNull(message = "El producto es obligatorio")
    @Column(name = "Producto_ID_Producto", nullable = false)
    private Long productoId;

    @NotNull(message = "La cantidad es obligatoria")
    @Column(name = "Cantidad", nullable = false)
    private Integer cantidad;

    @NotNull(message = "El subtotal no puede estar vacío")
    @Column(name = "Subtotal", nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "Venta_ID_Venta", nullable = false) 
    private Ventas venta; 


}
