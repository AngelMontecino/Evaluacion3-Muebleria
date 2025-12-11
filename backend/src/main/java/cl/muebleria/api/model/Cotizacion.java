 package cl.muebleria.api.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.FetchType;

@Entity
@Table(name = "cotizaciones")
public class Cotizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EstadoCotizacion estado;

   
  @JsonManagedReference
    @OneToMany(mappedBy = "cotizacion", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ItemCotizacion> items = new ArrayList<>();

    private double total; 

  
    public Cotizacion() {
        this.estado = EstadoCotizacion.PENDIENTE;
    }

    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public EstadoCotizacion getEstado() { return estado; }
    public void setEstado(EstadoCotizacion estado) { this.estado = estado; }
    public List<ItemCotizacion> getItems() { return items; }
    public void setItems(List<ItemCotizacion> items) { this.items = items; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

   
    public void addItem(ItemCotizacion item) {
        items.add(item);
        item.setCotizacion(this);
    }
}