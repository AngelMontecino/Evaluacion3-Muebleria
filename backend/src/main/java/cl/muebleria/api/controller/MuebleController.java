package cl.muebleria.api.controller;

import cl.muebleria.api.model.Mueble;
import cl.muebleria.api.service.CatalogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/muebles")
@CrossOrigin(origins = "*") 
public class MuebleController {

    @Autowired
    private CatalogoService catalogoService;

    // listar muebles
    @GetMapping
    public List<Mueble> listarMuebles() {
        return catalogoService.listarTodosLosMuebles();
    }

    // crear nuevo mueble
    @PostMapping
    public Mueble crearMueble(@RequestBody Mueble mueble) {
        return catalogoService.crearMueble(mueble);
    }

    // desactivar mueble
    @PutMapping("/{id}/desactivar")
    public Mueble desactivarMueble(@PathVariable Long id) {
        return catalogoService.desactivarMueble(id);
    }
    
    // activar mueble
    @PutMapping("/{id}/activar")
    public Mueble activarMueble(@PathVariable Long id) {
        return catalogoService.activarMueble(id);
    }
}