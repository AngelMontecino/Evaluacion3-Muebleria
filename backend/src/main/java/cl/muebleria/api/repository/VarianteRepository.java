package cl.muebleria.api.repository;

import cl.muebleria.api.model.Variante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VarianteRepository extends JpaRepository<Variante, Long> {
}