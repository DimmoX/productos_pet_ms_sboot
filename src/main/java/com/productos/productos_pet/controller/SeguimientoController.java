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

import com.productos.productos_pet.model.SeguimientoModel;
import com.productos.productos_pet.service.seguimientos.SeguimientoService;


@RestController
@RequestMapping("/seg")
public class SeguimientoController {

    private static final Logger logger = LoggerFactory.getLogger(SeguimientoController.class);

    @Autowired
    private SeguimientoService seguimientoService;

    /**
     * @description Metodo que maneja las peticiones GET al endpoint /seg
     * @return {Object} Lista de seguimientos
     */
    @GetMapping
    public ResponseEntity<List<SeguimientoModel>> getSeguimientos(){
        logger.info("GET: /seg -> Se obtienen todos los seguimientos");
        List<SeguimientoModel> seguimientos = seguimientoService.getSeguimientos();
        if(seguimientos.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        logger.info("GET: /seg -> Seguimientos encontrados: {}", seguimientos.size());
        return new ResponseEntity<>(seguimientos, HttpStatus.OK);
    }

    /**
     * @description Metodo que maneja las peticiones GET al endpoint /{id}
     * @param id
     * @return {Object} Seguimiento con el id especificado
     */
    @GetMapping("/{id}")
    public ResponseEntity<SeguimientoModel> getSeguimientoById(@PathVariable Long id){
        logger.info("GET: /seg/{} -> Obtener seguimiento", id);
        Optional<SeguimientoModel> seguimiento = seguimientoService.getSeguimientoById(id);
        if(!seguimiento.isPresent()){
            logger.error("GET: /seg/{} -> Seguimiento no encontrado", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(seguimiento.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SeguimientoModel> createSeguimiento(@RequestBody SeguimientoModel seguimiento){
        logger.info("POST: /seg -> Crear nuevo Seguimniento");
        SeguimientoModel saveSeg = seguimientoService.createSeguimiento(seguimiento);
        logger.info("POST: /seg -> Seguimiento creado, id nuevo seguimiento: {}", saveSeg.getId());
        return new ResponseEntity<>(saveSeg, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeguimientoModel> updateSeguimiento(@PathVariable Long id, @RequestBody SeguimientoModel seguimiento){
        logger.info("PUT: /seg/{} -> Actualizar seguimiento", id);
        if(!seguimientoService.getSeguimientoById(id).isPresent()){
            logger.error("PUT: /seg/{} -> Seguimientono encontrado", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("PUT: /seg/{} -> Seguimiento actualizado", id);
        return new ResponseEntity<>(seguimientoService.updateSeguimiento(id, seguimiento), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeguimiento(@PathVariable Long id){
        logger.info("DELETE: /seg/{} -> Eliminar seguimiento con id: {}", id);
        if(!seguimientoService.getSeguimientoById(id).isPresent()){
            logger.error("DELETE: /seg/{} -> Seguimiento no encontrado", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        seguimientoService.deleteSeguimiento(id);
        logger.info("DELETE: /seg/{} -> Seguimiento eliminado", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
}
