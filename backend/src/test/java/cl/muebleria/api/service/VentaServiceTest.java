 package cl.muebleria.api.service;

import cl.muebleria.api.model.*;
import cl.muebleria.api.repository.MuebleRepository;
import cl.muebleria.api.repository.CotizacionRepository;
import cl.muebleria.api.exception.StockInsuficienteException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class) // habilita Mockito
class VentaServiceTest {

    @Mock // simulacion del repositorio muebles
    private MuebleRepository muebleRepository;

    @Mock // simulacion del repositorio cotizaciones
    private CotizacionRepository cotizacionRepository;

    @InjectMocks // clase a probar
    private VentaService ventaService;

    // test de Venta 
    @Test
    void testConfirmarVenta_ConStockInsuficiente() {
        // 1. configuración 
        Mueble silla = new Mueble();
        silla.setIdMueble(1L);
        silla.setNombreMueble("Silla Gamer");
        silla.setStock(5); // solo hay 5 en stock

        ItemCotizacion item = new ItemCotizacion();
        item.setMueble(silla);
        item.setCantidad(10); // el cliente necesita 10

        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setId(1L);
        cotizacion.setEstado(EstadoCotizacion.PENDIENTE);
        cotizacion.setItems(List.of(item));

        // le decimos a Mockito qué debe responder cuando se le pregunte por la cotizacion
        when(cotizacionRepository.findById(1L)).thenReturn(Optional.of(cotizacion));

        // 2. ejecución y verificacion 
        
        // verificamos que se lanza la excepción correcta
        StockInsuficienteException exception = assertThrows(StockInsuficienteException.class, () -> {
            ventaService.confirmarVenta(1L); //   Llama al metodo que debe fallar
        });

        //    verificamos el mensaje de error 
        assertEquals("Stock insuficiente para: Silla Gamer", exception.getMessage());
        
        // verificamos que NUNCA se llamó a 'save' en el mueble
        verify(muebleRepository, never()).save(any(Mueble.class));
    }
    
    @Test
    void testConfirmarVenta_ConStockSuficiente() {
        // 1. configuración
        Mueble mesa = new Mueble();
        mesa.setIdMueble(2L);
        mesa.setStock(20); // hay 20
        
        ItemCotizacion item = new ItemCotizacion();
        item.setMueble(mesa);
        item.setCantidad(5); // El cliente quiere 5

        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setId(2L);
        cotizacion.setEstado(EstadoCotizacion.PENDIENTE);
        cotizacion.setItems(List.of(item));

        // Mock de las llamadas a la BD
        when(cotizacionRepository.findById(2L)).thenReturn(Optional.of(cotizacion));
        // Cuando se llame a guardar, simplemente devuelve la misma cotización
        when(cotizacionRepository.save(any(Cotizacion.class))).thenReturn(cotizacion); 

        // 2. ejecución 
        Cotizacion cotizacionVendida = ventaService.confirmarVenta(2L);

        // 3. verificación 
        
      
        assertEquals(15, mesa.getStock()); 
        
        // verficar que el método save SÍ fue llamado 1 vez en el MUEBLE
        verify(muebleRepository, times(1)).save(mesa);
        
        // verificar que el estado de la cotización es VENDIDA
        assertEquals(EstadoCotizacion.VENDIDA, cotizacionVendida.getEstado());
    }

    @Test
    void testCalcularPrecioItem() {
        // 1. configuracion 
        Mueble mesa = new Mueble();
        mesa.setPrecioBase(80000);

        Variante normal = new Variante();
        normal.setAumentoPrecio(0);

        Variante premium = new Variante();
        premium.setAumentoPrecio(25000);

        // 2. ejecución 
        // probar el precio normal con 2 unidades
        double precioNormal = ventaService.calcularPrecioItem(mesa, normal, 2);
        
        // probar el precio premium con 2 unidades
        double precioPremium = ventaService.calcularPrecioItem(mesa, premium, 2);


        // 3. verificacion 
      
        assertEquals(160000.0, precioNormal);
        
   
        assertEquals(210000.0, precioPremium);
    }
}