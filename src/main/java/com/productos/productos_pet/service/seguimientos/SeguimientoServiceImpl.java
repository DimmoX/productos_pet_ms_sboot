package com.productos.productos_pet.service.seguimientos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.productos.productos_pet.dto.SeguimientoDTO;
import com.productos.productos_pet.exceptionHandler.ResourceNotFoundException;
import com.productos.productos_pet.model.EnviosModel;
import com.productos.productos_pet.model.SeguimientoModel;
import com.productos.productos_pet.repository.EnviosRepository;
import com.productos.productos_pet.repository.SeguimientoRepository;


/**
 * @description Clase que maneja la ubicacion de los envios
 */

@Service
public class SeguimientoServiceImpl implements SeguimientoService {

    @Autowired
    private SeguimientoRepository seguimientoRepository;

    @Autowired
    private EnviosRepository enviosRepository;

    @Override
    public List<SeguimientoModel> getSeguimientos() {
        return seguimientoRepository.findAll();
    }

    @Override
    public Optional<SeguimientoModel> getSeguimientoById(Long id) {
        return seguimientoRepository.findById(id);
    }

    @Override
    public SeguimientoModel createSeguimiento(SeguimientoDTO seguimientoDTO) {

        SeguimientoModel seguimientoModel = new SeguimientoModel();

        seguimientoModel.setStatusSeguimiento(seguimientoDTO.getStatusSeguimiento());

        // Se parsear la fecha del DTO al modelo
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        seguimientoModel
                .setFechaUltimaActualizacion(LocalDate.parse(seguimientoDTO.getFechaUltimaActualizacion(), formatter));

        // Asignar el id del envío si existe
        Optional<EnviosModel> envioModel = enviosRepository.findById(seguimientoDTO.getIdEnvio());
        if (!envioModel.isPresent()) {
            throw new ResourceNotFoundException("Envio no Encontrado");
        }
        seguimientoModel.setIdEnvio(envioModel.get());

        // Guardar el seguimiento en la base de datos
        return seguimientoRepository.save(seguimientoModel);
    }

    @Override
    public SeguimientoModel updateSeguimiento(Long id, SeguimientoDTO seguimientoDTO) {

        Optional<SeguimientoModel> seguimientoModel = seguimientoRepository.findById(id);
        if (!seguimientoModel.isPresent()) {
            throw new ResourceNotFoundException("Seguimiento no Encontrado");
        }

        SeguimientoModel seguimiento = seguimientoModel.get();
        seguimiento.setStatusSeguimiento(seguimientoDTO.getStatusSeguimiento());

        // Parsear la fecha
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        seguimiento.setFechaUltimaActualizacion(LocalDate.parse(seguimientoDTO.getFechaUltimaActualizacion(), formatter));

        // Asignar el envío si existe
        Optional<EnviosModel> envioModel = enviosRepository.findById(seguimientoDTO.getIdEnvio());
        if (!envioModel.isPresent()) {
            throw new ResourceNotFoundException("Envio no Encontrado");
        }
        seguimiento.setIdEnvio(envioModel.get());

        // Guardar el seguimiento actualizado
        return seguimientoRepository.save(seguimiento);
    }

    @Override
    public void deleteSeguimiento(Long id) {
        seguimientoRepository.deleteById(id);
    }
}
