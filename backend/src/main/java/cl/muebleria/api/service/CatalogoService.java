package cl.muebleria.api.service;

import cl.muebleria.api.model.Mueble;
import cl.muebleria.api.model.EstadoMueble;
import cl.muebleria.api.repository.MuebleRepository;
import cl.muebleria.api.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CatalogoService {

    @Autowired
    private MuebleRepository muebleRepository;

    public Mueble crearMueble(Mueble mueble) {
      
        if (mueble.getPrecioBase() < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
        if (mueble.getStock() < 0) {
            throw new IllegalArgumentException("El stock inicial no puede ser negativo.");
        }
      

        mueble.setEstado(EstadoMueble.ACTIVO); // Por defecto
        return muebleRepository.save(mueble);
    }
    
    public List<Mueble> listarTodosLosMuebles() {
        return muebleRepository.findAll();
    }

    public Mueble getMueblePorId(Long id) {
        return muebleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mueble no encontrado con id: " + id));
    }
    
    public Mueble actualizarMueble(Long id, Mueble muebleDetalles) {
   
        if (muebleDetalles.getPrecioBase() < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
        if (muebleDetalles.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo.");
        }


        Mueble mueble = getMueblePorId(id); 

        mueble.setNombreMueble(muebleDetalles.getNombreMueble());
        mueble.setPrecioBase(muebleDetalles.getPrecioBase());
        mueble.setStock(muebleDetalles.getStock());
        mueble.setTipo(muebleDetalles.getTipo());
        mueble.setMaterial(muebleDetalles.getMaterial());
        mueble.setTamano(muebleDetalles.getTamano());

        return muebleRepository.save(mueble);
    }

    public Mueble desactivarMueble(Long id) {
        Mueble mueble = getMueblePorId(id);
        mueble.setEstado(EstadoMueble.INACTIVO);
        return muebleRepository.save(mueble);
    }

    public Mueble activarMueble(Long id) {
        Mueble mueble = getMueblePorId(id); 
        mueble.setEstado(EstadoMueble.ACTIVO);
        return muebleRepository.save(mueble);
    }
}