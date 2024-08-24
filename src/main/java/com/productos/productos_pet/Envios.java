package com.productos.productos_pet;

/**
 * @description Clase que maneja los envios
 */
public class Envios {
    // Atributos privados de la clase Envios
    private int idEnvios;
    private String nombreProducto;
    private String status;
    private String fechaEstimadaEntrega;

    /**
     * @description Constructor de la clase Envios
     * @param idEnvios
     * @param nombreProducto
     * @param status
     * @param fechaEstimadaEntrega
     */
    public Envios(int idEnvios, String nombreProducto, String status, String fechaEstimadaEntrega) {
        this.idEnvios = idEnvios;
        this.nombreProducto = nombreProducto;
        this.status = status;
        this.fechaEstimadaEntrega = fechaEstimadaEntrega;
    }

    /**
     * @description Metodo que retorna el id del envio
     * @return {int} idEnvios
     */
    public int getIdEnvio() {
        return idEnvios;
    }

    /**
     * @description Metodo que retorna el nombre del producto
     * @return {String} nombreProducto
     */
    public String getNombreProducto(){
        return nombreProducto;
    }

    /**
     * @description Metodo que retorna el status del envio
     * @return {String} status
     */
    public String getStatus(){
        return status;
    }

    /**
     * @description Metodo que retorna la fecha estimada de entrega
     * @return {String} fechaEstimadaEntrega
     */
    public String getFechaEstimadaEntrega(){
        return fechaEstimadaEntrega;
    }
}
