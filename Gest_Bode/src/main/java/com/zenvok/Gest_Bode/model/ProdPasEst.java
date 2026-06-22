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
@Table(name = "prod_pas_est")
public class ProdPasEst {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prod_est") 
    private Long idProdEst;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pas_est_id_pas_est", nullable = false)
    private Pas_Est pasEst;

    @Column(name = "producto_id_producto", nullable = false)
    private Long productoId;

}
