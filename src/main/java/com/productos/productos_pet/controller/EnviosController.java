package com.productos.productos_pet.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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
    public ResponseEntity<CollectionModel<EntityModel<EnviosModel>>> getEnvios() {
        logger.info("GET: /envios -> Se obtienen todos los envios");
        List<EntityModel<EnviosModel>> envios = enviosService.getEnvios().stream()
                .map(envio -> EntityModel.of(envio,
                        linkTo(methodOn(EnviosController.class).getEnvioById(envio.getId())).withSelfRel(),
                        linkTo(methodOn(EnviosController.class).getEnvios()).withRel("envios")))
                .collect(Collectors.toList());

        return new ResponseEntity<>(
                CollectionModel.of(envios, linkTo(methodOn(EnviosController.class).getEnvios()).withSelfRel()),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<EnviosModel>> getEnvioById(@PathVariable Long id) {
        logger.info("GET: /envios/{id} -> Se obtiene el envio con id: {}", id);
        Optional<EnviosModel> envio = enviosService.getEnvioById(id);
        if (!envio.isPresent()) {
            logger.error("GET: /envios/{id} -> No se encontro el envio con id: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        EntityModel<EnviosModel> entityModel = EntityModel.of(envio.get(),
                linkTo(methodOn(EnviosController.class).getEnvioById(id)).withSelfRel(),
                linkTo(methodOn(EnviosController.class).getEnvios()).withRel("envios"));

        return new ResponseEntity<>(entityModel, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EntityModel<EnviosModel>> createEnvio(@RequestBody EnviosModel envio) {
        logger.info("POST: /envios -> Se crea un nuevo envio");
        EnviosModel saveEnvio = enviosService.createEnvio(envio);
        EntityModel<EnviosModel> entityModel = EntityModel.of(saveEnvio,
                linkTo(methodOn(EnviosController.class).getEnvioById(saveEnvio.getId())).withSelfRel(),
                linkTo(methodOn(EnviosController.class).getEnvios()).withRel("envios"));

        return new ResponseEntity<>(entityModel, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<EnviosModel>> updateEnvio(@PathVariable Long id, @RequestBody EnviosModel envio) {
        logger.info("PUT: /envios/{id} -> Actualizar envio con id: {}", id);
        Optional<EnviosModel> existingEnvio = enviosService.getEnvioById(id);
        if (!existingEnvio.isPresent()) {
            logger.error("PUT: /envios/{id} -> No se encontro el envio con id: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        EnviosModel updatedEnvio = enviosService.updateEnvio(id, envio);

        logger.info("PUT: /envios/{id} -> Se actualizo el envio con id: {}", id);
        EntityModel<EnviosModel> entityModel = EntityModel.of(updatedEnvio,
                linkTo(methodOn(EnviosController.class).getEnvioById(id)).withSelfRel(),
                linkTo(methodOn(EnviosController.class).getEnvios()).withRel("envios"));

        return new ResponseEntity<>(entityModel, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnvio(@PathVariable Long id) {
        logger.info("DELETE: /envios/{id} -> Eliminar envio con id: {}", id);
        Optional<EnviosModel> envio = enviosService.getEnvioById(id);
        if (!envio.isPresent()) {
            logger.error("DELETE: /envios/{id} -> No se encontro el envio con id: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        enviosService.deleteEnvio(id);
        logger.info("DELETE: /envios/{id} -> Se elimino el envio con id: {}", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
