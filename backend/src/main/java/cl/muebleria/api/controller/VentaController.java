package cl.muebleria.api.controller;

import cl.muebleria.api.dto.CotizacionRequestDTO;
import cl.muebleria.api.model.Cotizacion;
import cl.muebleria.api.model.Variante;
import cl.muebleria.api.repository.VarianteRepository;
import cl.muebleria.api.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@CrossOrigin(origins = "*")
public class VentaController {

    @Autowired
    private VentaService ventaService;
    @Autowired
    private VarianteRepository varianteRepository;

    // crear cotización
    @PostMapping("/cotizar")
    public Cotizacion crearCotizacion(@RequestBody CotizacionRequestDTO request) {
        return ventaService.crearCotizacion(request);
    }

    // confirmar venta
    @PostMapping("/confirmar/{id}")
    public Cotizacion confirmarVenta(@PathVariable Long id) {
        return ventaService.confirmarVenta(id);
    }
    
    // cancelar cotización
    @PutMapping("/cancelar/{id}")
    public Cotizacion cancelarCotizacion(@PathVariable Long id) {
        return ventaService.cancelarCotizacion(id);
    }

    // listar  variantes
    @GetMapping("/variantes")
    public List<Variante> listarVariantes() {
        return varianteRepository.findAll();
    }

    // crear Nueva Variante
    @PostMapping("/variantes")
    public Variante crearVariante(@RequestBody Variante variante) {
        return ventaService.crearVariante(variante);
    }
    
    // listar cotizaciones
    @GetMapping("/cotizaciones")
    public List<Cotizacion> listarCotizaciones() {
        return ventaService.listarTodasLasCotizaciones();
    }
}