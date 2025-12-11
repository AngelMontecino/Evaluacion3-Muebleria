package cl.muebleria.api;

import cl.muebleria.api.model.Mueble;
import cl.muebleria.api.model.Variante;
import cl.muebleria.api.model.TamanoMueble;
import cl.muebleria.api.repository.VarianteRepository;
import cl.muebleria.api.service.CatalogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TestConsolaRunner implements CommandLineRunner {

    @Autowired
    private CatalogoService catalogoService;

    @Autowired
    private VarianteRepository varianteRepository;

    @Override
    public void run(String... args) throws Exception {
        crearDatosIniciales();
    }

    private void crearDatosIniciales() {
        //  Crear Variantes si no existen
        if (varianteRepository.count() == 0) {
            System.out.println(">> Creando Variantes iniciales...");
            
            Variante normal = new Variante();
            normal.setNombre("Normal");
            normal.setAumentoPrecio(0);
            varianteRepository.save(normal);

            Variante premium = new Variante();
            premium.setNombre("Barniz Premium");
            premium.setAumentoPrecio(30000);
            varianteRepository.save(premium);
        }

        // Crear Muebles si no existen
        if (catalogoService.listarTodosLosMuebles().isEmpty()) {
            System.out.println(" Creando Muebles iniciales...");
            
            Mueble silla = new Mueble();
            silla.setNombreMueble("Silla Gamer");
            silla.setTipo("Silla");
            silla.setPrecioBase(55000);
            silla.setStock(123);
            silla.setTamano(TamanoMueble.MEDIANO);
            silla.setMaterial("Tela");
            catalogoService.crearMueble(silla);

            Mueble mesa = new Mueble();
            mesa.setNombreMueble("Mesa de Comedor");
            mesa.setTipo("Mesa");
            mesa.setPrecioBase(77000);
            mesa.setStock(68);
            mesa.setTamano(TamanoMueble.GRANDE);
            mesa.setMaterial("Madera");
            catalogoService.crearMueble(mesa);
        }
    }
}