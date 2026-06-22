package com.zenvok.gestionusuario.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tipos_usuarios")

public class TipoUsuario {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "Administrador|Bodeguero|Repartidor|Cliente",
    message = "El tipo de usuario debe ser Administrador, Bodeguero, Repartidor o Cliente")
    @Column(nullable = false)
    private String nombreTipo;

    @OneToMany(mappedBy = "tipoUsuario")
    private List<Usuario> usuarios;

    public TipoUsuario(Long id, String nombreTipo) {
        this.id = id;
        this.nombreTipo = nombreTipo;
    }

}
