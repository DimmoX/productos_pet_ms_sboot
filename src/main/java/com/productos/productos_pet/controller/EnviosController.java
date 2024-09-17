package com.productos.productos_pet.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.productos.productos_pet.model.EnviosModel;
import com.productos.productos_pet.service.envios.EnviosService;

@RestController
@RequestMapping(path = "/envios")
public class EnviosController {

    private static final Logger logger = LoggerFactory.getLogger(EnviosController.class);

    @Autowired
    private EnviosService enviosService;

    @GetMapping
    public ResponseEntity<List<EnviosModel>> getEnvios(){
        logger.info("GET: /envios -> Se obtienen todos los envios");
        List<EnviosModel> envios = enviosService.getEnvios();
        if( envios.isEmpty() ){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("GET: /envios -> envíos encontrados: {}", envios.size());
        return new ResponseEntity<>(envios, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<EnviosModel> getEnvioById(@PathVariable Long id) {
        logger.info("GET: /envios/{} -> Obtener envio", id);
        Optional<EnviosModel> envio = enviosService.getEnvioById(id);
        if (!envio.isPresent()) {
            logger.error("GET: /envios/{} -> Envio no encontrado", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("GET: /envios/{} -> Envio encontrado", id);
        return new ResponseEntity<>(envio.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EnviosModel> createEnvio(@RequestBody EnviosModel envios) {
        logger.info("POST: /envios -> Crear nuevo envio");
        EnviosModel saveEnvio = enviosService.createEnvio(envios);
        logger.info("POST: /envios -> Envio creado", saveEnvio.getId());
        return new ResponseEntity<>(saveEnvio, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnviosModel> updtaeEnvio(@PathVariable Long id, @RequestBody EnviosModel envios) {
        logger.info("PUT: /envios/{} -> Actualizando envio", id);
        Optional<EnviosModel> envio = enviosService.getEnvioById(id);
        if (!envio.isPresent()) {
            logger.error("PUT: /envios/{} -> Envio no encontrado", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("PUT: /envios/{} -> Envio encontrado", id);
        return new ResponseEntity<>(enviosService.updateEnvio(id, envios), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnvio(@PathVariable Long id) {
        logger.info("DELETE: /envios/{} -> Eliminando envio", id);
        Optional<EnviosModel> envio = enviosService.getEnvioById(id);
        if (!envio.isPresent()) {
            logger.error("DELETE: /envios/{} -> Envio no encontrado", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        enviosService.deleteEnvio(id);
        logger.info("DELETE: /envios/{} -> Envio encontrado", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}