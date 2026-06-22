package com.zenvok.direccion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zenvok.direccion.model.Comuna;


public interface ComunaRepository extends JpaRepository<Comuna, Long> {

    Optional<Comuna> findByNombreComunaIgnoreCase (String nombreComuna);



}
