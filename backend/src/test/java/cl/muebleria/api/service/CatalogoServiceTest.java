package cl.muebleria.api.service;

import cl.muebleria.api.model.Mueble;
import cl.muebleria.api.model.EstadoMueble;
import cl.muebleria.api.repository.MuebleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CatalogoServiceTest {

    @Mock // simulacion del repositorio muebles
    private MuebleRepository muebleRepository;

    @InjectMocks // inyeccion de los mocks en la clase a probar
    private CatalogoService catalogoService;

    @Test
    void testCrearMueble() {
        // 1. configuración 
        Mueble silla = new Mueble();
        silla.setNombreMueble("Silla de Prueba");
        
        // 2. ejecución 
        catalogoService.crearMueble(silla);

        // 3. verificación 
        // verificamos que el estado se asignó correctamente a ACTIVO
        assertEquals(EstadoMueble.ACTIVO, silla.getEstado());
        
        // verificamos que el método 'save' fue llamado exactamente 1 vez
        verify(muebleRepository, times(1)).save(silla);
    }

    @Test
    void testDesactivarMueble() {
        // 1. configuración 
        Mueble silla = new Mueble();
        silla.setIdMueble(1L);
        silla.setNombreMueble("Silla a Desactivar");
        silla.setEstado(EstadoMueble.ACTIVO); // Estado inicial
        
        // le decimos a Mockito que devuelva esta silla cuando la busquen por ID
        when(muebleRepository.findById(1L)).thenReturn(Optional.of(silla));

        // 2. ejecución (Act)
        catalogoService.desactivarMueble(1L);

        // 3. verificación 
        // capturamos el objeto que se intentó guardar
        ArgumentCaptor<Mueble> muebleCaptor = ArgumentCaptor.forClass(Mueble.class);
        verify(muebleRepository).save(muebleCaptor.capture());
        
        // verificamos que el estado del objeto guardado es INACTIVO
        assertEquals(EstadoMueble.INACTIVO, muebleCaptor.getValue().getEstado());
    }
}