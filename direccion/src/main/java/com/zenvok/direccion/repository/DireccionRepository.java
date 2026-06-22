package com.zenvok.direccion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zenvok.direccion.model.Direccion;

public interface DireccionRepository extends JpaRepository<Direccion, Long> {
    
    List<Direccion> findByUsuarioId(Long usuarioId);
    List<Direccion> findByComuna_Id(Long comunaId);
    List<Direccion> findByEstadoId(Long estadoId);

}
