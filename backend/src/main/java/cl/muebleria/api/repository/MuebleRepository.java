 package cl.muebleria.api.repository;

import cl.muebleria.api.model.Mueble;
import cl.muebleria.api.model.EstadoMueble;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MuebleRepository extends JpaRepository<Mueble, Long> {

    List<Mueble> findByEstado(EstadoMueble estado);
}