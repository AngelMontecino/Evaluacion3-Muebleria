package cl.muebleria.api;

import cl.muebleria.api.dto.CotizacionRequestDTO;
import cl.muebleria.api.dto.ItemCotizacionDTO;
import cl.muebleria.api.model.Cotizacion;
import cl.muebleria.api.model.Mueble;
import cl.muebleria.api.model.TamanoMueble;
import cl.muebleria.api.model.Variante;
import cl.muebleria.api.repository.VarianteRepository;
import cl.muebleria.api.service.CatalogoService;
import cl.muebleria.api.service.VentaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

// @Component
public class TestConsolaRunner implements CommandLineRunner, ApplicationContextAware {
    private ApplicationContext context;

    @Autowired
    private CatalogoService catalogoService;
    @Autowired
    private VentaService ventaService;
    @Autowired
    private VarianteRepository varianteRepository;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.context = applicationContext;
    }

    @Override
    public void run(String... args) throws Exception {
        
        crearDatosIniciales();

        Scanner scanner = new Scanner(System.in);
     
      
   

        while (true) {
            System.out.println("");
            System.out.println("--- PRUEBA DE MUEBLERÍA ---");
            System.out.println("  1. Listar Muebles ");
            System.out.println("  2. Listar Variantes");
            System.out.println("  3. Listar Cotizaciones");
            System.out.println("  4. Crear Cotización ");
            System.out.println("  5. Confirmar Venta ");
            System.out.println("  --- Administración ---");
            System.out.println("  6. Crear Nuevo Mueble");
            System.out.println("  7. Crear Nueva Variante");
            System.out.println("  8. Desactivar un Mueble");
            System.out.println("  9. Activar un Mueble");
            System.out.println("  10. Cancelar Cotización"); 
            System.out.println("  --------------------");
            System.out.println("  0. Salir y APAGAR servidor");
            System.out.print("Elige una opción: ");

            String opcionStr = scanner.nextLine(); 
            try {
                int opcion = Integer.parseInt(opcionStr);
                
                switch (opcion) {
                    case 1:
                        probarListarMuebles();
                        break;

                    case 2:
                        probarListarVariantes();
                        break;

                     case 3:
                        probarListarCotizaciones();
                        break;
                        
                    case 4:
                        probarCrearCotizacion(scanner);
                        break;
                    case 5:
                        probarConfirmarVenta(scanner);
                        break;
                    case 6:
                        probarCrearMueble(scanner);
                        break;
                    case 7:
                        probarCrearVariante(scanner);
                        break;
                    case 8:
                        probarDesactivarMueble(scanner);
                        break;
                    case 9:
                        probarActivarMueble(scanner);
                        break;
                   
                    case 10: 
                        probarCancelarCotizacion(scanner);
                        break;
                    case 0:
                        System.out.println("... Saliendo y apagando el servidor.");
                        ((ConfigurableApplicationContext) context).close();
                        return;
                    default:
                        System.out.println("ADVERTENCIA: Opción no válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("ADVERTENCIA: Por favor, ingresa solo un número.");
            } catch (Exception e) {
                System.out.println("¡ERROR EN LA OPERACIÓN!: " + e.getMessage());
            }
        }
    }

    private void probarListarMuebles() {
        System.out.println("--- Listando Muebles (Todos) ---");
        List<Mueble> muebles = catalogoService.listarTodosLosMuebles();
        if (muebles.isEmpty()) {
            System.out.println("No hay muebles en el catálogo.");
            return;
        }
        for (Mueble mueble : muebles) {
            System.out.printf("  ID: %d | Nombre: %s | Tamaño: %s | Precio: $%.0f | Stock: %d | Estado: %s\n",
                    mueble.getIdMueble(),
                    mueble.getNombreMueble(),
                    mueble.getTamano(),
                    mueble.getPrecioBase(),
                    mueble.getStock(),
                    mueble.getEstado());
        }
    }

    private void probarCrearCotizacion(Scanner scanner) {
        System.out.println("--- Probando Crear Cotización ---");
        System.out.print("  > ID del Mueble a cotizar: ");
        Long muebleId = Long.parseLong(scanner.nextLine());
        System.out.print("  > ID de la Variante (ej: 1=Normal): ");
        Long varianteId = Long.parseLong(scanner.nextLine());
        System.out.print("  > Cantidad: ");
        int cantidad = Integer.parseInt(scanner.nextLine());
        
        ItemCotizacionDTO item = new ItemCotizacionDTO();
        item.setMuebleId(muebleId);
        item.setVarianteId(varianteId);
        item.setCantidad(cantidad);
        
        CotizacionRequestDTO request = new CotizacionRequestDTO();
        request.setItems(List.of(item));
        
        Cotizacion cotizacion = ventaService.crearCotizacion(request);
        System.out.println("¡Cotización Creada con Éxito!");
        System.out.printf("  > ID Cotización: %d | Total: $%.0f | Estado: %s\n",
                cotizacion.getId(),
                cotizacion.getTotal(),
                cotizacion.getEstado());
    }

    private void probarConfirmarVenta(Scanner scanner) {
        System.out.println("--- Probando Confirmar Venta ---");
        System.out.print("  > ID de la Cotización a confirmar: ");
        Long cotizacionId = Long.parseLong(scanner.nextLine());

        Cotizacion cotizacion = ventaService.confirmarVenta(cotizacionId);
        System.out.println("¡Venta Confirmada con Éxito!");
        System.out.printf("  > Estado: %s\n", cotizacion.getEstado());
        System.out.println("  > El stock ha sido descontado.");
    }

    private void probarCrearMueble(Scanner scanner) {
        System.out.println("--- Creando Nuevo Mueble ---");
        try {
            Mueble mueble = new Mueble();

            System.out.print("  > Nombre del mueble: ");
            mueble.setNombreMueble(scanner.nextLine());

            System.out.print("  > Tipo (ej: Silla, Mesa): ");
            mueble.setTipo(scanner.nextLine());
            
            System.out.print("  > Material (ej: Madera, Tela): ");
            mueble.setMaterial(scanner.nextLine());

            System.out.print("  > Precio Base (ej: 50000): ");
            mueble.setPrecioBase(Double.parseDouble(scanner.nextLine()));

            System.out.print("  > Stock inicial (ej: 100): ");
            mueble.setStock(Integer.parseInt(scanner.nextLine()));

            System.out.print("  > Tamaño (GRANDE, MEDIANO, PEQUENO): ");
            mueble.setTamano(TamanoMueble.valueOf(scanner.nextLine().toUpperCase()));

            Mueble muebleCreado = catalogoService.crearMueble(mueble);
            System.out.println("¡Mueble creado con ID: " + muebleCreado.getIdMueble() + "!");

        } catch (IllegalArgumentException e) {
            System.out.println("¡ERROR! El tamaño no es válido. Debe ser GRANDE, MEDIANO o PEQUENO.");
        } catch (Exception e) {
            System.out.println("¡ERROR! Asegúrate de ingresar números válidos para precio y stock.");
        }
    }

    private void probarCrearVariante(Scanner scanner) {
        System.out.println("--- Creando Nueva Variante ---");
        try {
            Variante variante = new Variante();

            System.out.print("  > Nombre de la variante (ej: Ruedas de goma): ");
            variante.setNombre(scanner.nextLine());

            System.out.print("  > Aumento de Precio: ");
            variante.setAumentoPrecio(Double.parseDouble(scanner.nextLine()));

            varianteRepository.save(variante);
            System.out.println("¡Variante creada con éxito!");

        } catch (Exception e) {
            System.out.println("¡ERROR! Asegúrate de ingresar un número válido para el precio.");
        }
    }

    private void probarDesactivarMueble(Scanner scanner) {
        System.out.println("--- Desactivando Mueble ---");
        System.out.print("  > ID del Mueble a desactivar: ");
        Long muebleId = Long.parseLong(scanner.nextLine());

        Mueble mueble = catalogoService.desactivarMueble(muebleId);
        System.out.println("¡Mueble '" + mueble.getNombreMueble() + "' desactivado con éxito!");
        System.out.println("  > Su nuevo estado es: " + mueble.getEstado());
    }

    private void probarActivarMueble(Scanner scanner) {
        System.out.println("--- Activando Mueble ---");
        System.out.print("  > ID del Mueble a activar: ");
        Long muebleId = Long.parseLong(scanner.nextLine());

        Mueble mueble = catalogoService.activarMueble(muebleId);
        System.out.println("¡Mueble '" + mueble.getNombreMueble() + "' activado con éxito!");
        System.out.println("  > Su nuevo estado es: " + mueble.getEstado());
    }
    
    private void probarListarVariantes() {
        System.out.println("--- Listando Variantes Registradas ---");
        List<Variante> variantes = varianteRepository.findAll();
        if (variantes.isEmpty()) {
            System.out.println("No hay variantes registradas.");
            return;
        }
        for (Variante v : variantes) {
            System.out.printf("  ID: %d | Nombre: %s | Aumento de Precio: $%.0f\n",
                    v.getId(),
                    v.getNombre(),
                    v.getAumentoPrecio());
        }
    }

    private void probarListarCotizaciones() {
        System.out.println("--- Listando Cotizaciones (Todas) ---");
        List<Cotizacion> cotizaciones = ventaService.listarTodasLasCotizaciones();
        
        if (cotizaciones.isEmpty()) {
            System.out.println("No hay cotizaciones registradas.");
            return;
        }

        for (Cotizacion c : cotizaciones) {
            System.out.printf("  ID Cotización: %d | Estado: %s | Total: $%.0f | Items: %d\n",
                    c.getId(),
                    c.getEstado(),
                    c.getTotal(),
                    c.getItems().size());
        }
    }

   
    private void probarCancelarCotizacion(Scanner scanner) {
        System.out.println("--- Cancelando Cotización ---");
        System.out.print("  > ID de la Cotización a cancelar: ");
        Long cotizacionId = Long.parseLong(scanner.nextLine());

       
        Cotizacion cotizacion = ventaService.cancelarCotizacion(cotizacionId); 
        
        System.out.println("¡Cotización cancelada con éxito!");
        System.out.printf("  > ID: %d | Nuevo Estado: %s\n", 
                cotizacion.getId(),
                cotizacion.getEstado());
    }
    
    // Datos iniciales para pruebas

    private void crearDatosIniciales() {
        if (varianteRepository.count() == 0) {
            System.out.println("No hay variantes. Creando Variantes iniciales...");
            Variante normal = new Variante();
            normal.setNombre("Normal");
            normal.setAumentoPrecio(0);
            varianteRepository.save(normal);
            Variante premium = new Variante();
            premium.setNombre("Barniz Premium");
            premium.setAumentoPrecio(25000);
            varianteRepository.save(premium);
        }

        if (catalogoService.listarTodosLosMuebles().isEmpty()) {
            System.out.println("No hay muebles. Creando Muebles iniciales...");
            Mueble silla = new Mueble();
            silla.setNombreMueble("Silla de Oficina");
            silla.setTipo("Silla");
            silla.setPrecioBase(50000);
            silla.setStock(100);
            silla.setTamano(cl.muebleria.api.model.TamanoMueble.MEDIANO);
            silla.setMaterial("Tela");
            catalogoService.crearMueble(silla);
            Mueble mesa = new Mueble();
            mesa.setNombreMueble("Mesa de Centro");
            mesa.setTipo("Mesa");
            mesa.setPrecioBase(80000);
            mesa.setStock(50);
            mesa.setTamano(cl.muebleria.api.model.TamanoMueble.GRANDE);
            mesa.setMaterial("Madera");
            catalogoService.crearMueble(mesa);
        }
    }
}