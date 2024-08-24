package com.productos.productos_pet;

// Se importan las librerias necesarias
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Se importan las librerias necesarias de Spring
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description Clase que maneja las peticiones GET a los endpoint /envios, /seg y /paquetes
 */
@RestController
public class ProductosPetController {
    // Se inicialilza las listas de envios y seguimientos
    private List<Envios> envios = new ArrayList<>();
    private List<Ubicacion> seguimientos = new ArrayList<>();

    /**
     * @description Constructor de la clase ProductosPetController
     */
    public ProductosPetController(){
        // Se pobla lista de envios
        envios.add(new Envios(1, "Cama para Perro", "Preparando", "10-09-2024"));
        envios.add(new Envios(2, "Atún para Gatos", "Enviado", "01-09-2024"));
        envios.add(new Envios(3, "Snack de pollo con atún", "Enviado", "23-08-2024"));

        // Se pobla lista de seguimientos
        seguimientos.add(new Ubicacion(3,"Entregado", "23-08-2024"));
        seguimientos.add(new Ubicacion(1, "En Almacen", "22-08-2024"));
        seguimientos.add(new Ubicacion(2, "En Reparto", "21-08-2024"));
    }

    /**
     * @description Metodo que maneja las peticiones GET al endpoint /envios
     * @return {Object} Lista de envios
     */
    @GetMapping(path = "/envios")
    public List<Envios> getEnvios(){
        return envios;
    }

    /**
     * @description Metodo que maneja las peticiones GET al endpoint /envios/{id}
     * @param id
     * @return {Object} Envio con el id especificado
     */
    @GetMapping(path = "/envios/{id}")
    public List<Envios> getEnvioById(@PathVariable int id) {
        for(Envios envio: envios){
            if(envio.getIdEnvio() == id){
                return envio;
            }
        }
    }
    
    /**
     * @description Metodo que maneja las peticiones GET al endpoint /seg
     * @return {Object} Lista de seguimientos
     */
    @GetMapping(path = "/seg")
    public List<Ubicacion> getSeguimientos(){
        return seguimientos;
    }

    /**
     * @description Metodo que maneja las peticiones GET al endpoint /seg/{id}
     * @param id
     * @return {Object} Seguimiento con el id especificado
     */
    @GetMapping(path = "/seg/{id}")
    public Ubicacion getSeguimientoById(@PathVariable int id){
        for(Ubicacion seguimiento : seguimientos){
            if(seguimiento.getIdEnvio() == id){
                return seguimiento;
            }
        }
        return null;
    }

    /**
     * @description Metodo que maneja las peticiones GET al endpoint /paquete/{id}
     * @param id
     * @return {Object} Informacion completa del paquete con el id especificado
     */
    @GetMapping(path = "/paquete/{id}")
    public Map<String, Object> getInfoPaquete(@PathVariable int id) {
        return new Paquete().getInfoPaquete(id, envios, seguimientos);
    }
}