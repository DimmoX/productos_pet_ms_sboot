package com.productos.productos_pet.model;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.productos.productos_pet.model.enums.StatusSegEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "seguimiento_productos")
public class SeguimientoModel extends RepresentationModel<SeguimientoModel>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Size(min = 8, max = 16, message = "El status del seguimiento debe tener entre 8 y 16 caracteres")
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status_seg")
    private StatusSegEnum statusSeguimiento;
    
    @NotNull
    @Column(name = "fecha_ult_actu")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-[0-9]{4}$", message = "La fecha debe tener el formato MM-DD-YYYY")
    private LocalDate fechaUltimaActualizacion;

    @OneToOne
    @JoinColumn(name = "idEnvio")
    private EnviosModel idEnvio;


    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
  
    public StatusSegEnum getStatusSeguimiento() {
        return statusSeguimiento;
    }

    public void setStatusSeguimiento(StatusSegEnum statusSeguimiento) {
        this.statusSeguimiento = statusSeguimiento;
    }

    public LocalDate getFechaUltimaActualizacion() {
        return fechaUltimaActualizacion;
    }

    public void setFechaUltimaActualizacion(LocalDate fechaUltimaActualizacion) {
        this.fechaUltimaActualizacion = fechaUltimaActualizacion;
    }

    public EnviosModel getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(EnviosModel idEnvio) {
        this.idEnvio = idEnvio;
    }
}
