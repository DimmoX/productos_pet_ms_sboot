package com.productos.productos_pet.model.enums;

public enum StatusEnvioEnum {
    PROCESANDO("procesando"),
    ENVIADO("enviado"),
    EN_TRANSITO("en tránsito"),
    ENTREGADO("entregado");

     private String value;

    StatusEnvioEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
