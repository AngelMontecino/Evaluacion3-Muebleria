package cl.muebleria.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "variantes")
public class Variante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre; 
    private double aumentoPrecio; 

    
    public Variante() {
    }

    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getAumentoPrecio() {
        return aumentoPrecio;
    }

    public void setAumentoPrecio(double aumentoPrecio) {
        this.aumentoPrecio = aumentoPrecio;
    }
}