package com.productos.productos_pet;
// Se importan las librerias necesarias
import java.util.List;
import java.util.Map;

/**
 * @description Clase que maneja los paquetes
 */
public class Paquete {
    
    /**
     * @description Metodo que retorna la informacion de un paquete
     * @param id
     * @param envios
     * @param seguimientos
     * @return {Object} Informacion del paquete
     */
    public Map<String, Object> getInfoPaquete(int id, List<Envios> envios, List<Ubicacion> seguimientos){
        for(Envios envio : envios){
            for(Ubicacion seg : seguimientos){
                if(envio.getIdEnvio() == id && seg.getIdEnvio() == id){
                    return Map.of(
                        "idEnvio", envio.getIdEnvio(), 
                        "nombreProducto", envio.getNombreProducto(), 
                        "status", envio.getStatus(), 
                        "fechaEstimadaEntrega", envio.getFechaEstimadaEntrega(), 
                        "ubicacionActual", seg.getUbicacionActual(), 
                        "ultimaActualizacion", seg.getUltimaActualizacion()
                    );
                };
            };
        };
        return Map.of("error", "No se encontro el paquete");
    }
}
