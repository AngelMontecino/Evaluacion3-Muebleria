package cl.muebleria.api.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "items_cotizacion")
public class ItemCotizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // muchos items pueden apuntar a un mueble
    @JoinColumn(name = "mueble_id", nullable = false)
    private Mueble mueble;

    @ManyToOne // muchos items pueden apuntar a una variante
    @JoinColumn(name = "variante_id", nullable = false)
    private Variante variante;

    private int cantidad;
    @JsonBackReference
    @ManyToOne // muchhos items pueden pertenecer a una cotización
    @JoinColumn(name = "cotizacion_id")
    private Cotizacion cotizacion;

 
    public ItemCotizacion() {
    }

   
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Mueble getMueble() { return mueble; }
    public void setMueble(Mueble mueble) { this.mueble = mueble; }
    public Variante getVariante() { return variante; }
    public void setVariante(Variante variante) { this.variante = variante; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public Cotizacion getCotizacion() { return cotizacion; }
    public void setCotizacion(Cotizacion cotizacion) { this.cotizacion = cotizacion; }
}