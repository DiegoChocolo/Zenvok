package com.zenvok.Gest_Bode.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pasillo")
public class Pasillo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pasillo")
    private Long idPasillo;

    @NotBlank(message = "El nombre del pasillo es obligatorio")
    @Column(name = "nombre_pasillo", nullable = false, length = 50)
    private String nombrePasillo;
}
