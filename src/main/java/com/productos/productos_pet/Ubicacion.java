package com.productos.productos_pet;

/**
 * @description Clase que maneja la ubicacion de los envios
 */
public class Ubicacion {
    // Atributos privados de la clase Ubicacion
    private int idEnvio;
    private String ubicacionActual;
    private String ultimaActualizacion;

    /**
     * @description Constructor de la clase Ubicacion
     * @param idEnvio
     * @param ubicacionActual
     * @param ultimaActualizacion
     */
    public Ubicacion(int idEnvio, String ubicacionActual, String ultimaActualizacion) {
        this.idEnvio = idEnvio;
        this.ubicacionActual = ubicacionActual;
        this.ultimaActualizacion = ultimaActualizacion;
    }

    /**
     * @description Metodo que retorna el id del envio
     * @return {int} idEnvio
     */
    public int getIdEnvio() {
        return idEnvio;
    }

    /**
     * @description Metodo que retorna la ubicacion actual del envio
     * @return {String} ubicacionActual
     */
    public String getUbicacionActual(){
        return ubicacionActual;
    }

    /**
     * @description Metodo que retorna la ultima actualizacion del envio
     * @return {String} ultimaActualizacion
     */
    public String getUltimaActualizacion(){
        return ultimaActualizacion;
    }
}
