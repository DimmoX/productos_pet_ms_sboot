package com.productos.productos_pet.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.productos.productos_pet.model.EnviosModel;
import com.productos.productos_pet.model.enums.StatusEnvioEnum;
import com.productos.productos_pet.service.envios.EnviosService;

@WebMvcTest(EnviosController.class)
public class EnviosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Usa @MockBean para crear un mock del servicio
    @MockBean
    private EnviosService enviosService;

    @InjectMocks
    private EnviosController enviosController;

    @BeforeEach
    void configuracionInicial() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(enviosController).build();
    }

    @AfterEach
    void limpiarRecursos() {
    }

    @Test
    @DisplayName("Test Obtener Todos los Envíos")
    public void testGetEnvios() {
        // Configuración de una lista de envíos de prueba
        EnviosModel envio1 = new EnviosModel();
        envio1.setId(1L);
        envio1.setNombreProducto("Producto 1");
        envio1.setStatusEnvio(StatusEnvioEnum.EN_TRANSITO);
        envio1.setFechaEstimadaEntrega(LocalDate.of(2024, 10, 5));

        EnviosModel envio2 = new EnviosModel();
        envio2.setId(2L);
        envio2.setNombreProducto("Producto 2");
        envio2.setStatusEnvio(StatusEnvioEnum.ENTREGADO);
        envio2.setFechaEstimadaEntrega(LocalDate.of(2024, 10, 6));

        // Simulación del servicio
        when(enviosService.getEnvios()).thenReturn(Arrays.asList(envio1, envio2));

        // Llamada directa al método del controlador
        ResponseEntity<CollectionModel<EntityModel<EnviosModel>>> response = enviosController.getEnvios();

        // Verificación del estado HTTP
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verificación del contenido de la respuesta
        CollectionModel<EntityModel<EnviosModel>> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(2, responseBody.getContent().size());

        List<EntityModel<EnviosModel>> enviosList = new ArrayList<>(responseBody.getContent());
        assertEquals(envio1.getId(), enviosList.get(0).getContent().getId());
        assertEquals(envio1.getNombreProducto(), enviosList.get(0).getContent().getNombreProducto());
        assertEquals(envio2.getId(), enviosList.get(1).getContent().getId());
        assertEquals(envio2.getNombreProducto(), enviosList.get(1).getContent().getNombreProducto());

        // Verificación de que el servicio fue llamado correctamente
        verify(enviosService, times(1)).getEnvios();
    }


    @Test
    @DisplayName("Test Crear Envío")
    public void testCreateEnvio() {
        // Configuración del objeto de prueba
        EnviosModel envio = new EnviosModel();
        envio.setId(1L);
        envio.setNombreProducto("Nuevo Producto");
        envio.setStatusEnvio(StatusEnvioEnum.PROCESANDO);
        envio.setFechaEstimadaEntrega(LocalDate.of(2024, 12, 31));

        // Simulación de las dependencias del servicio
        when(enviosService.createEnvio(any(EnviosModel.class))).thenReturn(envio);

        // Llamada directa al método del controlador
        ResponseEntity<EntityModel<EnviosModel>> response = enviosController.createEnvio(envio);

        // Verificación del estado HTTP
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Verificación de que el contenido sea el esperado
        EntityModel<EnviosModel> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(envio.getId(), responseBody.getContent().getId());
        assertEquals(envio.getNombreProducto(), responseBody.getContent().getNombreProducto());
        assertEquals(envio.getStatusEnvio(), responseBody.getContent().getStatusEnvio());
        assertEquals(envio.getFechaEstimadaEntrega(), responseBody.getContent().getFechaEstimadaEntrega());

        // Verificación de que el servicio fue llamado correctamente
        verify(enviosService, times(1)).createEnvio(any(EnviosModel.class));
    }


}
