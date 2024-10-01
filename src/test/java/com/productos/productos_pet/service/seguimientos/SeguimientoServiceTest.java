package com.productos.productos_pet.service.seguimientos;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

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

import com.productos.productos_pet.dto.SeguimientoDTO;
import com.productos.productos_pet.model.EnviosModel;
import com.productos.productos_pet.model.SeguimientoModel;
import com.productos.productos_pet.model.enums.StatusEnvioEnum;
import com.productos.productos_pet.model.enums.StatusSegEnum;
import com.productos.productos_pet.repository.EnviosRepository;
import com.productos.productos_pet.repository.SeguimientoRepository;
import com.productos.productos_pet.service.envios.EnviosServiceImpl;

@ExtendWith(MockitoExtension.class)
public class SeguimientoServiceTest {

    @Mock
    private SeguimientoRepository seguimientoRepositoryMock;

    @Mock
    private EnviosRepository enviosRepositoryMock;

    @InjectMocks
    private SeguimientoServiceImpl seguimientoService;

    @InjectMocks
    private EnviosServiceImpl enviosService;

    private Long seguimientoId;
    private Long envioId;
    private SeguimientoModel seguimiento;
    private EnviosModel envio;
    private DateTimeFormatter formatter;
    private SeguimientoDTO seguimientoDTO;

    @BeforeEach
    public void configuracionInicial() {
        // Se inicializa los Mocks
        MockitoAnnotations.openMocks(this);

        // IDs de prueba
        seguimientoId = 1L;
        envioId = 5L;

        // Formato de fecha permitida
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        // Crear el DTO con estado EN_TRANSITO
        seguimientoDTO = new SeguimientoDTO();
        seguimientoDTO.setStatusSeguimiento(StatusSegEnum.EN_TRANSITO); // Estado actualizado
        seguimientoDTO.setFechaUltimaActualizacion("01-10-2024");
        seguimientoDTO.setIdEnvio(envioId);

        // Crear el seguimiento inicial con estado LISTO_PARA_ENVIO
        seguimiento = new SeguimientoModel();
        seguimiento.setId(seguimientoId);
        seguimiento.setStatusSeguimiento(StatusSegEnum.LISTO_PARA_ENVIO); // Estado original
        seguimiento.setFechaUltimaActualizacion(LocalDate.parse("30-09-2024", formatter));

        // Crear el envío
        envio = new EnviosModel();
        envio.setId(envioId);
        envio.setStatusEnvio(StatusEnvioEnum.ENVIADO);

        seguimiento.setIdEnvio(envio); // Asignar el envío al seguimiento
    }

    @AfterEach
    public void limpiarRecursos() {
        // Se restablece los mocks
        Mockito.reset(seguimientoRepositoryMock, enviosRepositoryMock);

        seguimientoId = null;
        envio = null;
        seguimientoDTO = null;

    }

    @Test
    @DisplayName("Test Crear Seguimiento")
    public void testCreateSeguimiento() {
        // Simular que el envío existe
        when(enviosRepositoryMock.findById(envioId)).thenReturn(Optional.of(envio));

        // Simular que el seguimiento se guarda correctamente
        when(seguimientoRepositoryMock.save(any(SeguimientoModel.class))).thenReturn(seguimiento);

        // Ejecutar el método del servicio usando la variable seguimientoDTO
        SeguimientoModel result = seguimientoService.createSeguimiento(seguimientoDTO);

        // Verificar que se ejecuta y se guardan los datos
        verify(enviosRepositoryMock, times(1)).findById(envioId);
        verify(seguimientoRepositoryMock, times(1)).save(any(SeguimientoModel.class));

        // Se verifican los resultados
        assertNotNull(result);
        assertEquals(seguimiento.getId(), result.getId());
        assertEquals(seguimiento.getStatusSeguimiento(), result.getStatusSeguimiento());
        assertEquals(seguimiento.getFechaUltimaActualizacion(), result.getFechaUltimaActualizacion());
        assertEquals(seguimiento.getIdEnvio().getId(), result.getIdEnvio().getId());

    }

    @Test
    @DisplayName("Test Actualizar Seguimiento")
    void testUpdateSeguimiento() {
        // Simular que el seguimiento y el envío existen
        when(seguimientoRepositoryMock.findById(seguimientoId)).thenReturn(Optional.of(seguimiento));
        when(enviosRepositoryMock.findById(envioId)).thenReturn(Optional.of(envio));

        // Se crea un nuevo objeto que simula el seguimiento actualizado
        SeguimientoModel updateSeguimiento = new SeguimientoModel();
        updateSeguimiento.setId(seguimientoId);
        updateSeguimiento.setStatusSeguimiento(StatusSegEnum.EN_TRANSITO);
        updateSeguimiento.setFechaUltimaActualizacion(LocalDate.parse("01-10-2024", formatter));
        updateSeguimiento.setIdEnvio(envio);

        when(seguimientoRepositoryMock.save(any(SeguimientoModel.class))).thenReturn(updateSeguimiento);

        SeguimientoModel seguimientoOriginal = new SeguimientoModel();
        seguimientoOriginal.setId(seguimiento.getId());
        seguimientoOriginal.setStatusSeguimiento(seguimiento.getStatusSeguimiento());
        seguimientoOriginal.setFechaUltimaActualizacion(seguimiento.getFechaUltimaActualizacion());
        seguimientoOriginal.setIdEnvio(seguimiento.getIdEnvio());

        // Se ejcuta el método del servicio
        SeguimientoModel result = seguimientoService.updateSeguimiento(seguimientoId, seguimientoDTO);

        // Se verifica que los métodos se llamaron correctamente
        verify(seguimientoRepositoryMock, times(1)).findById(seguimientoId);
        verify(enviosRepositoryMock, times(1)).findById(envioId);
        verify(seguimientoRepositoryMock, times(1)).save(any(SeguimientoModel.class));

        // Se valida que el resultado no sea null
        assertNotNull(result);
        // Se verifica que los datos del seguimiento se hayan actualizado
        assertNotEquals(seguimientoOriginal.getStatusSeguimiento(), result.getStatusSeguimiento());
        assertNotEquals(seguimientoOriginal.getFechaUltimaActualizacion(), result.getFechaUltimaActualizacion());
        assertEquals(seguimientoOriginal.getIdEnvio().getId(), result.getIdEnvio().getId());
    }
}
