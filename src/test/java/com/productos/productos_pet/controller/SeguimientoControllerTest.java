package com.productos.productos_pet.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.productos.productos_pet.dto.SeguimientoDTO;
import com.productos.productos_pet.model.EnviosModel;
import com.productos.productos_pet.model.SeguimientoModel;
import com.productos.productos_pet.model.enums.StatusSegEnum;
import com.productos.productos_pet.repository.EnviosRepository;
import com.productos.productos_pet.repository.SeguimientoRepository;
import com.productos.productos_pet.service.seguimientos.SeguimientoService;

@WebMvcTest(SeguimientoController.class)
public class SeguimientoControllerTest {

    @Autowired
    private MockMvc mockMvc;  // Simula la interacción con los endpoints

    @InjectMocks
    private SeguimientoController seguimientoController;  // Inyecta los mocks en el controlador

    @MockBean
    private SeguimientoService seguimientoService;  // Simula el servicio

    @MockBean
    private EnviosRepository enviosRepositoryMock;  // Simula el repositorio de envíos

    @MockBean
    private SeguimientoRepository seguimientoRepositoryMock;  // Simula el repositorio de seguimientos

    private Long seguimientoId;
    private Long envioId;
    private SeguimientoModel seguimiento;
    private EnviosModel envio;
    private DateTimeFormatter formatter;
    private SeguimientoDTO seguimientoDTO;

    @BeforeEach
    void configuracionInicial() {
        MockitoAnnotations.openMocks(this);

        // IDs de prueba
        seguimientoId = 1L;
        envioId = 5L;
        
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        // Crear el DTO de seguimiento
        seguimientoDTO = new SeguimientoDTO();
        seguimientoDTO.setStatusSeguimiento(StatusSegEnum.EN_TRANSITO);
        seguimientoDTO.setFechaUltimaActualizacion("01-10-2024");
        seguimientoDTO.setIdEnvio(envioId);

        // Crear el modelo de envío
        envio = new EnviosModel();
        envio.setId(envioId);

        // Crear el seguimiento model
        seguimiento = new SeguimientoModel();
        seguimiento.setId(1L);
        seguimiento.setStatusSeguimiento(StatusSegEnum.EN_TRANSITO);
        seguimiento.setFechaUltimaActualizacion(LocalDate.parse("01-10-2024", formatter));
        seguimiento.setIdEnvio(envio);
    }

    @AfterEach
    void limpiarRecursos()  {
        Mockito.reset(seguimientoRepositoryMock, enviosRepositoryMock);


        seguimientoId = null;
        envio = null;
        seguimientoDTO = null;
    }

    @Test
    @DisplayName("Test Obtener Todos los Seguimientos")
    public void testGetSeguimientos() {
        // Configuración de objetos EnviosModel de prueba
        EnviosModel envio1 = new EnviosModel();
        envio1.setId(1L); // Especifica un ID para el envío asociado

        EnviosModel envio2 = new EnviosModel();
        envio2.setId(2L); // Especifica un ID para el envío asociado

        // Configuración de una lista de seguimientos de prueba
        SeguimientoModel seguimiento1 = new SeguimientoModel();
        seguimiento1.setId(1L);
        seguimiento1.setStatusSeguimiento(StatusSegEnum.RECIBIDO);
        seguimiento1.setFechaUltimaActualizacion(LocalDate.of(2024, 10, 5));
        seguimiento1.setIdEnvio(envio1); // Asigna el envío asociado

        SeguimientoModel seguimiento2 = new SeguimientoModel();
        seguimiento2.setId(2L);
        seguimiento2.setStatusSeguimiento(StatusSegEnum.LISTO_PARA_ENVIO);
        seguimiento2.setFechaUltimaActualizacion(LocalDate.of(2024, 10, 6));
        seguimiento2.setIdEnvio(envio2); // Asigna el envío asociado

        // Simulación del servicio
        when(seguimientoService.getSeguimientos()).thenReturn(Arrays.asList(seguimiento1, seguimiento2));

        // Llamada directa al método del controlador
        ResponseEntity<CollectionModel<EntityModel<SeguimientoModel>>> response = seguimientoController.getSeguimientos();

        // Verificación del estado HTTP
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verificación del contenido de la respuesta
        CollectionModel<EntityModel<SeguimientoModel>> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(2, responseBody.getContent().size());

        List<EntityModel<SeguimientoModel>> seguimientosList = new ArrayList<>(responseBody.getContent());
        assertEquals(seguimiento1.getId(), seguimientosList.get(0).getContent().getId());
        assertEquals(seguimiento1.getStatusSeguimiento(), seguimientosList.get(0).getContent().getStatusSeguimiento());
        assertEquals(seguimiento2.getId(), seguimientosList.get(1).getContent().getId());
        assertEquals(seguimiento2.getStatusSeguimiento(), seguimientosList.get(1).getContent().getStatusSeguimiento());

        // Verificación de que el servicio fue llamado correctamente
        verify(seguimientoService, times(1)).getSeguimientos();
    }

    @Test
    @DisplayName("Test Crear Seguimiento en el Controller")
    public void testCreateSeguimiento() {
        // Configuración del objeto SeguimientoDTO
        seguimientoDTO.setIdEnvio(envioId);
        seguimientoDTO.setStatusSeguimiento(StatusSegEnum.EN_TRANSITO);
        seguimientoDTO.setFechaUltimaActualizacion("05-10-2024");

        // Simulación del envío relacionado
        when(enviosRepositoryMock.findById(envioId)).thenReturn(Optional.of(envio));

        // Simulación de creación de seguimiento con ID asignado
        seguimiento.setId(1L); // Asegúrate de que el seguimiento tenga un ID asignado
        when(seguimientoService.createSeguimiento(any(SeguimientoDTO.class))).thenReturn(seguimiento);

        // Llamada directa al método del controlador usando la instancia del controlador
        ResponseEntity<EntityModel<SeguimientoModel>> response = seguimientoController.createSeguimiento(seguimientoDTO);

        // Verificación del estado HTTP
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verificación de que el contenido sea el esperado
        EntityModel<SeguimientoModel> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(seguimiento.getId(), responseBody.getContent().getId());
        assertEquals(seguimiento.getStatusSeguimiento(), responseBody.getContent().getStatusSeguimiento());
        assertEquals(seguimiento.getFechaUltimaActualizacion(), responseBody.getContent().getFechaUltimaActualizacion());
        assertEquals(seguimiento.getIdEnvio().getId(), responseBody.getContent().getIdEnvio().getId());

        // Verificación de que los métodos fueron llamados correctamente
        verify(enviosRepositoryMock, times(1)).findById(envioId);
        verify(seguimientoService, times(1)).createSeguimiento(any(SeguimientoDTO.class));
    }

}
