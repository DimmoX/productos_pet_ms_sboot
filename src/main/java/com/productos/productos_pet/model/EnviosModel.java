package com.productos.productos_pet.model;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.productos.productos_pet.model.enums.StatusEnvioEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "envio_productos")
public class EnviosModel extends RepresentationModel<EnviosModel>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    @Column(name = "id")
    private Long id;
    
    @NotNull
    @Size(max = 50, message = "El nombre del producto debe tener un máximo de 50 caracteres")
    @Column(name = "nombre_prod")
    private String nombreProducto;

    @Size(min = 7, max = 11, message = "El status del envío debe tener entre 7 y 11 caracteres")
    @Enumerated(EnumType.STRING)
    @Column(name = "status_env")
    @NotNull
    private StatusEnvioEnum statusEnvio;

    @Column(name = "fecha_est_ent")
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-[0-9]{4}$", message = "La fecha debe tener el formato MM-DD-YYYY")
    private LocalDate fechaEstimadaEntrega;

    @OneToOne(mappedBy = "idEnvio", cascade = CascadeType.ALL)
    private SeguimientoModel seguimiento;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public StatusEnvioEnum getStatusEnvio() {
        return statusEnvio;
    }

    public void setStatusEnvio(StatusEnvioEnum statusEnvio) {
        this.statusEnvio = statusEnvio;
    }

    public LocalDate getFechaEstimadaEntrega() {
        return fechaEstimadaEntrega;
    }

    public void setFechaEstimadaEntrega(LocalDate fechaEstimadaEntrega) {
        this.fechaEstimadaEntrega = fechaEstimadaEntrega;
    }
}
