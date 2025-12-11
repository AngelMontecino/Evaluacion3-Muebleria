 package cl.muebleria.api.repository;

import cl.muebleria.api.model.Cotizacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CotizacionRepository extends JpaRepository<Cotizacion, Long> {
}