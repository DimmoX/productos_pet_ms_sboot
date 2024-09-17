package com.productos.productos_pet.service.seguimientos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.productos.productos_pet.model.SeguimientoModel;
import com.productos.productos_pet.repository.SeguimientoRepository;


/**
 * @description Clase que maneja la ubicacion de los envios
 */

@Service
public class SeguimientoServiceImpl implements SeguimientoService {

    @Autowired
    private SeguimientoRepository seguimientoRepository;

    @Override
    public List<SeguimientoModel> getSeguimientos() {
        return seguimientoRepository.findAll();
    }

    @Override
    public Optional<SeguimientoModel> getSeguimientoById(Long id) {
        return seguimientoRepository.findById(id);
    }

    @Override
    public SeguimientoModel createSeguimiento(SeguimientoModel seguimiento) {
        return seguimientoRepository.save(seguimiento);
    }

    @Override
    public SeguimientoModel updateSeguimiento(Long id, SeguimientoModel seguimiento) {
        if(!seguimientoRepository.existsById(id)){
            return null;
        };
    
        seguimiento.setId(id);
        return seguimientoRepository.save(seguimiento);
    }

    @Override
    public void deleteSeguimiento(Long id) {
        seguimientoRepository.deleteById(id);
    }
}
