package com.zenvok.Gest_Bode.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pas_est")
public class Pas_Est {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pas_est")
    private Long idPasEst;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pasillo_id_pasillo", nullable = false)
    private Pasillo pasillo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estante_id_estante", nullable = false)
    private Estante estante;

}
