package com.productos.productos_pet.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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

import com.productos.productos_pet.dto.SeguimientoDTO;
import com.productos.productos_pet.model.EnviosModel;
import com.productos.productos_pet.model.SeguimientoModel;
import com.productos.productos_pet.repository.EnviosRepository;
import com.productos.productos_pet.repository.SeguimientoRepository;
import com.productos.productos_pet.service.seguimientos.SeguimientoService;

@RestController
@RequestMapping("/seg")
public class SeguimientoController {

    private static final Logger logger = LoggerFactory.getLogger(SeguimientoController.class);

    @Autowired
    private SeguimientoService seguimientoService;

    @Autowired
    private SeguimientoRepository seguimientoRepository;

    @Autowired
    private EnviosRepository enviosRepository;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<SeguimientoModel>>> getSeguimientos() {
        logger.info("GET: /seg -> Se obtienen todos los seguimientos");
        List<EntityModel<SeguimientoModel>> seguimientos = seguimientoService.getSeguimientos().stream()
                .map(seguimiento -> {
                    EnviosModel envio = seguimiento.getIdEnvio();
                    return EntityModel.of(seguimiento,
                        linkTo(methodOn(SeguimientoController.class).getSeguimientoById(seguimiento.getId()))
                                .withSelfRel(),
                        linkTo(methodOn(SeguimientoController.class).getSeguimientos()).withRel("seguimientos"),
                        linkTo(methodOn(EnviosController.class).getEnvioById(envio.getId())).withRel("envio"),
                        linkTo(methodOn(EnviosController.class).getEnvios()).withRel("envios"));
                })
                .collect(Collectors.toList());

        return new ResponseEntity<>(CollectionModel.of(seguimientos,
                linkTo(methodOn(SeguimientoController.class).getSeguimientos()).withSelfRel()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<SeguimientoModel>> getSeguimientoById(@PathVariable Long id) {
        logger.info("GET: /seg/{id} -> Obtener el seguimiento con id: {}", id);
        Optional<SeguimientoModel> seguimiento = seguimientoService.getSeguimientoById(id);
        if (!seguimiento.isPresent()) {
            logger.error("GET: /seg/{id} -> No se encontro el seguimiento con id: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("GET: /seg/{id} -> Se encontro el seguimiento con id: {}", id);
        EntityModel<SeguimientoModel> entityModel = EntityModel.of(seguimiento.get(),
                linkTo(methodOn(SeguimientoController.class).getSeguimientoById(id)).withSelfRel(),
                linkTo(methodOn(SeguimientoController.class).getSeguimientos()).withRel("seguimientos"));

        return new ResponseEntity<>(entityModel, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EntityModel<SeguimientoModel>> createSeguimiento(@RequestBody SeguimientoDTO seguimientoDTO) {
        logger.info("POST: /seg -> Crear un nuevo seguimiento");
        // Buscar el envío relacionado
        EnviosModel envio = enviosRepository.findById(seguimientoDTO.getIdEnvio())
            .orElseThrow(() -> {
                    logger.error("Envío no encontrado para el ID: {}", seguimientoDTO.getIdEnvio());
                    return new RuntimeException("Envío no encontrado");
                });

        // Crear el nuevo seguimiento
        SeguimientoModel seguimiento = new SeguimientoModel();
        seguimiento.setStatusSeguimiento(seguimientoDTO.getStatusSeguimiento());
        seguimiento.setFechaUltimaActualizacion(LocalDate.parse(seguimientoDTO.getFechaUltimaActualizacion(), DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        seguimiento.setIdEnvio(envio);

        seguimientoRepository.save(seguimiento);

        // Crear el modelo de entidad con enlaces HATEOAS
        EntityModel<SeguimientoModel> entityModel = EntityModel.of(seguimiento,
            // Enlace a sí mismo (al detalle del seguimiento)
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SeguimientoController.class).getSeguimientoById(seguimiento.getId())).withSelfRel(),
            // Enlace al envío relacionado
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EnviosController.class).getEnvioById(envio.getId())).withRel("envio"),
            // Enlace a la lista de Envios
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EnviosController.class).getEnvios()).withRel("envios"),
            // Enlace a la lista de Seguimientos
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SeguimientoController.class).getSeguimientos()).withRel("seguimientos")
        );

        logger.info("POST: /seg -> Se creó un nuevo seguimiento con ID: {}", seguimiento.getId());

        // Retornar la respuesta con el seguimiento y los enlaces
        return ResponseEntity.ok(entityModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<SeguimientoModel>> updateSeguimiento(@PathVariable Long id,
            @RequestBody SeguimientoDTO seguimientoDTO) {
        logger.info("PUT: /seg/{id} -> Actualizar seguimiento con id: {}", id);
        Optional<SeguimientoModel> existingSeguimiento = seguimientoService.getSeguimientoById(id);
        if (!existingSeguimiento.isPresent()) {
            logger.error("PUT: /seg/{id} -> No se encontro el seguimiento con id: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        SeguimientoModel updatedSeguimiento = seguimientoService.updateSeguimiento(id, seguimientoDTO);
        EntityModel<SeguimientoModel> entityModel = EntityModel.of(updatedSeguimiento,
                linkTo(methodOn(SeguimientoController.class).getSeguimientoById(id)).withSelfRel(),
                linkTo(methodOn(SeguimientoController.class).getSeguimientos()).withRel("seguimientos"));

        logger.info("PUT: /seg/{id} -> Se actualizo el seguimiento con id: {}", id);
        return new ResponseEntity<>(entityModel, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeguimiento(@PathVariable Long id) {
        logger.info("DELETE: /seg/{id} -> Eliminar seguimiento con id: {}", id);
        Optional<SeguimientoModel> seguimiento = seguimientoService.getSeguimientoById(id);
        if (!seguimiento.isPresent()) {
            logger.error("DELETE: /seg/{id} -> No se encontro el seguimiento con id: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        seguimientoService.deleteSeguimiento(id);
        logger.info("DELETE: /seg/{id} -> Se elimino el seguimiento con id: {}", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
