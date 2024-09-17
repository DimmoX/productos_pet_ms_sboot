package com.productos.productos_pet.service.envios;

import java.util.List;
import java.util.Optional;

import com.productos.productos_pet.model.EnviosModel;

public interface EnviosService {

    List<EnviosModel> getEnvios();

    Optional<EnviosModel> getEnvioById(Long id);

    EnviosModel createEnvio(EnviosModel envio);

    EnviosModel updateEnvio(Long id, EnviosModel envio);

    void deleteEnvio(Long id);
}
