package com.productos.productos_pet.model.enums;

public enum StatusSegEnum {
    RECIBIDO("recibido"),
    LISTO_PARA_ENVIO("listo para envío"),
    EN_TRANSITO("en tránsito"),
    ENTREGADO("entregado");

     private String value;

    StatusSegEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
