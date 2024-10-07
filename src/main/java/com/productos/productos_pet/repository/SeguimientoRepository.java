package com.productos.productos_pet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.productos.productos_pet.model.SeguimientoModel;

@Repository
public interface SeguimientoRepository extends JpaRepository<SeguimientoModel, Long> {

}