package com.productos.productos_pet.service.seguimientos;

import java.util.List;
import java.util.Optional;

import com.productos.productos_pet.dto.SeguimientoDTO;
import com.productos.productos_pet.model.SeguimientoModel;

public interface SeguimientoService {

    List<SeguimientoModel> getSeguimientos();

    Optional<SeguimientoModel> getSeguimientoById(Long id);

    SeguimientoModel createSeguimiento(SeguimientoDTO seguimientoDTO);

    SeguimientoModel updateSeguimiento(Long id, SeguimientoDTO seguimientoDTO);

    void deleteSeguimiento(Long id);

}
