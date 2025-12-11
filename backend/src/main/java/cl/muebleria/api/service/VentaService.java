 package cl.muebleria.api.service;

import cl.muebleria.api.model.*;
import cl.muebleria.api.repository.*;
import cl.muebleria.api.exception.*;
import cl.muebleria.api.dto.CotizacionRequestDTO;
import cl.muebleria.api.dto.ItemCotizacionDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class VentaService {

    @Autowired
    private MuebleRepository muebleRepository;
    @Autowired
    private VarianteRepository varianteRepository;
    @Autowired
    private CotizacionRepository cotizacionRepository;

    // calculo de precio 
    public double calcularPrecioItem(Mueble mueble, Variante variante, int cantidad) {
        double precioUnitario = mueble.getPrecioBase();
        
     
        if (variante.getAumentoPrecio() > 0) {
            precioUnitario += variante.getAumentoPrecio();
        }
        return precioUnitario * cantidad;
    }

    // Crear una Cotización 
    @Transactional
    public Cotizacion crearCotizacion(CotizacionRequestDTO requestDTO) {
        Cotizacion cotizacion = new Cotizacion();
        double totalCotizacion = 0;

        for (ItemCotizacionDTO itemDTO : requestDTO.getItems()) {
            
         
            Mueble mueble = muebleRepository.findById(itemDTO.getMuebleId())
                    .orElseThrow(() -> new ResourceNotFoundException("Mueble no encontrado"));
            
            Variante variante = varianteRepository.findById(itemDTO.getVarianteId())
                    .orElseThrow(() -> new ResourceNotFoundException("Variante no encontrada"));

      
            ItemCotizacion item = new ItemCotizacion();
            item.setMueble(mueble);
            item.setVariante(variante);
            item.setCantidad(itemDTO.getCantidad());
            
        
            cotizacion.addItem(item);

         
            totalCotizacion += calcularPrecioItem(mueble, variante, itemDTO.getCantidad());
        }

        cotizacion.setTotal(totalCotizacion);
        return cotizacionRepository.save(cotizacion);
    }


    // Confirmar Venta 
    @Transactional 
    public Cotizacion confirmarVenta(Long cotizacionId) {
        
        Cotizacion cotizacion = cotizacionRepository.findById(cotizacionId)
                .orElseThrow(() -> new ResourceNotFoundException("Cotización no encontrada"));

        if (cotizacion.getEstado() == EstadoCotizacion.VENDIDA) {
            throw new RuntimeException("Esta cotización ya fue vendida.");
        }

    
        for (ItemCotizacion item : cotizacion.getItems()) {
            Mueble mueble = item.getMueble();
            if (mueble.getStock() < item.getCantidad()) {
                throw new StockInsuficienteException("Stock insuficiente para: " + mueble.getNombreMueble());
            }
        }
        
       
        for (ItemCotizacion item : cotizacion.getItems()) {
            Mueble mueble = item.getMueble();
            int nuevoStock = mueble.getStock() - item.getCantidad();
            mueble.setStock(nuevoStock);
            muebleRepository.save(mueble); 
        }

        cotizacion.setEstado(EstadoCotizacion.VENDIDA);
        return cotizacionRepository.save(cotizacion);
    }
    
    public List<Cotizacion> listarTodasLasCotizaciones() {
        return cotizacionRepository.findAll();
    }

    
    @Transactional
    public Cotizacion cancelarCotizacion(Long cotizacionId) {
        
        Cotizacion cotizacion = cotizacionRepository.findById(cotizacionId)
                .orElseThrow(() -> new ResourceNotFoundException("Cotización no encontrada")); //

        if (cotizacion.getEstado() == EstadoCotizacion.VENDIDA) { //
            throw new RuntimeException("No se puede cancelar una cotización que ya fue vendIDA.");
        }
        
        if (cotizacion.getEstado() == EstadoCotizacion.CANCELADA) { //
            throw new RuntimeException("Esta cotización ya se encuentra cancelada.");
        }

        cotizacion.setEstado(EstadoCotizacion.CANCELADA); //
        return cotizacionRepository.save(cotizacion);
    }
}