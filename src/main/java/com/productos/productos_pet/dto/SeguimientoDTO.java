package com.productos.productos_pet.dto;

import com.productos.productos_pet.model.enums.StatusSegEnum;

public class SeguimientoDTO {
    private StatusSegEnum statusSeguimiento;
    private String fechaUltimaActualizacion;
    private Long idEnvio;

    // Getters y Setters
    public StatusSegEnum getStatusSeguimiento() {
        return statusSeguimiento;
    }

    public void setStatusSeguimiento(StatusSegEnum statusSeguimiento) {
        this.statusSeguimiento = statusSeguimiento;
    }

    public String getFechaUltimaActualizacion() {
        return fechaUltimaActualizacion;
    }

    public void setFechaUltimaActualizacion(String fechaUltimaActualizacion) {
        this.fechaUltimaActualizacion = fechaUltimaActualizacion;
    }

    public Long getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(Long idEnvio) {
        this.idEnvio = idEnvio;
    }
}
