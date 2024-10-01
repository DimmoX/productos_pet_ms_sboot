package com.productos.productos_pet.service.envios;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.productos.productos_pet.model.EnviosModel;
import com.productos.productos_pet.model.enums.StatusEnvioEnum;
import com.productos.productos_pet.repository.EnviosRepository;

@ExtendWith(MockitoExtension.class)
public class EnviosServiceTest {

    @Mock
    private EnviosRepository enviosRepositoryMock;

    @InjectMocks
    private EnviosServiceImpl enviosService;

    private Long envioId;
    private EnviosModel envio;
    private EnviosModel envioUpdate;

    @BeforeEach
    public void configuracionInicial() {
        // Se inicializa los mocks
        MockitoAnnotations.openMocks(this);
        envioId = 1L;

        // Se crea un formato de fecha permitido
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        // se crea objeto envio y se le asigna parámetros
        envio = new EnviosModel();
        envio.setId(envioId);
        envio.setNombreProducto("Producto 1");
        envio.setStatusEnvio(StatusEnvioEnum.PROCESANDO);
        envio.setFechaEstimadaEntrega(LocalDate.parse("01-10-2024", formatter));

        // Se crea objeto envioUpdate y se le asigna parámetros
        envioUpdate = new EnviosModel();
        envioUpdate.setId(envioId);
        envioUpdate.setNombreProducto("Producto 1");
        envioUpdate.setStatusEnvio(StatusEnvioEnum.ENVIADO);
        envioUpdate.setFechaEstimadaEntrega(LocalDate.parse("01-10-2024", formatter));
    }

    @AfterEach
    public void limpiarRecursos() {
        // Se resetean los Mocks después de cada prueba
        Mockito.reset(enviosRepositoryMock);

        envioId = null;
    }

    @Test
    @DisplayName("Test Crear Envío")
    public void testCreateEnvio() {
        // Se simula que el envío se guarda correctamente
        when(enviosRepositoryMock.save(envio)).thenReturn(envio);

        // Se ejecuta el método para crear envio del Service
        EnviosModel result = enviosService.createEnvio(envio);

        // Se verifica que se ejecuta el método save
        verify(enviosRepositoryMock, times(1)).save(envio);

        // Se verifican los resultados
        assertNotNull(result);
        assertEquals(envioId, result.getId());
        assertEquals("Producto 1", result.getNombreProducto());
        assertEquals(StatusEnvioEnum.PROCESANDO, result.getStatusEnvio());
        assertEquals(LocalDate.parse("01-10-2024", DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                result.getFechaEstimadaEntrega());
    }

    @Test
    @DisplayName("Test Actualizar Envío")
    public void testUpdateEnvio() {

        // Se simula la existencia de un envio según el id
        when(enviosRepositoryMock.existsById(envioId)).thenReturn(true);
        // Se simula el guardado del envio actualizado
        when(enviosRepositoryMock.save(envioUpdate)).thenReturn(envioUpdate);

        // Ejecutar el método del servicio
        EnviosModel result = enviosService.updateEnvio(envioId, envioUpdate);

        // Se verifica que se ejecuten los métodos existsById y save
        verify(enviosRepositoryMock, times(1)).existsById(envioId);
        verify(enviosRepositoryMock, times(1)).save(envioUpdate);

        // Se verifican los resultados
        assertNotNull(result);
        assertEquals(envio.getId(), result.getId());
        assertEquals(envio.getNombreProducto(), result.getNombreProducto());
        assertNotEquals(envio.getStatusEnvio(), result.getStatusEnvio());
        assertEquals(envio.getFechaEstimadaEntrega(), result.getFechaEstimadaEntrega());

        
    }
}