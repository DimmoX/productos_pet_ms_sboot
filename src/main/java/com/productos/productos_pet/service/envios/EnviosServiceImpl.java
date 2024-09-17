package com.productos.productos_pet.service.envios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.productos.productos_pet.model.EnviosModel;
import com.productos.productos_pet.repository.EnviosRepository;

/**
 * @description Clase que maneja los envios
 */

@Service
public class EnviosServiceImpl implements EnviosService {
    // Atributos privados de la clase Envios
    
    @Autowired
    private EnviosRepository enviosRepository;

    @Override
    public List<EnviosModel> getEnvios() {
        return enviosRepository.findAll();
    }

    @Override
    public Optional<EnviosModel> getEnvioById(Long id) {
        return enviosRepository.findById(id);
    }
    
    @Override
    public EnviosModel createEnvio(EnviosModel envio) {
        return enviosRepository.save(envio);
    }

    @Override
    public EnviosModel updateEnvio(Long id, EnviosModel envio) {
        if(!enviosRepository.existsById(id)){
            return null;
        };
    
        envio.setId(id);
        return enviosRepository.save(envio);
    }

    @Override
    public void deleteEnvio(Long id) {
        enviosRepository.deleteById(id);
    }
}
