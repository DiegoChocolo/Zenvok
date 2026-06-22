package com.zenvok.envios.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "envios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Envios")
    private Long id;

    @Column(name = "F_Envios", nullable = false, length = 20)
    private String fechaEnvio;

    @Column(name = "F_Embargue", nullable = false, length = 20)
    private String fechaEmbargue;

    @Column(name = "Comentarios", length = 350)
    private String comentarios;

    @Column(name = "Direccion_ID_Direccion", nullable = false, length = 60)
    private Long direccionId;

    @Column(name = "Venta_ID_Venta", nullable = false, length = 60)
    private Long ventaId;

    @Column(name = "Estado_ID_Estado", nullable = false, length = 60)
    private Long estadoId;
}
