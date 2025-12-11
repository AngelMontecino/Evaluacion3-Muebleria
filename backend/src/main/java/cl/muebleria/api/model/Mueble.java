package cl.muebleria.api.model;

import jakarta.persistence.*;

@Entity // Le dice a JPA que esto es una tabla
@Table(name = "muebles") // Nombre de la tabla
public class Mueble {

    @Id // Marca esto como la llave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincremental
    private Long idMueble;

    private String nombreMueble;
    private String tipo; // "silla", "mesa", etc.
    private double precioBase;
    private int stock;

    @Enumerated(EnumType.STRING) // Guarda "ACTIVO" o "INACTIVO" en la BD
    private EstadoMueble estado;

    @Enumerated(EnumType.STRING) // Guarda "GRANDE", "MEDIANO", "PEQUENO"
    private TamanoMueble tamano;

    private String material;

    // --- Constructores ---
    
    // Constructor vacío 
    public Mueble() {
    }

    // Constructor con campos 
    public Mueble(String nombreMueble, String tipo, double precioBase, int stock, EstadoMueble estado, TamanoMueble tamano, String material) {
        this.nombreMueble = nombreMueble;
        this.tipo = tipo;
        this.precioBase = precioBase;
        this.stock = stock;
        this.estado = estado;
        this.tamano = tamano;
        this.material = material;
    }

    // --- Getters y Setters ---


    public Long getIdMueble() {
        return idMueble;
    }

    public void setIdMueble(Long idMueble) {
        this.idMueble = idMueble;
    }

    public String getNombreMueble() {
        return nombreMueble;
    }

    public void setNombreMueble(String nombreMueble) {
        this.nombreMueble = nombreMueble;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public EstadoMueble getEstado() {
        return estado;
    }

    public void setEstado(EstadoMueble estado) {
        this.estado = estado;
    }

    public TamanoMueble getTamano() {
        return tamano;
    }

    public void setTamano(TamanoMueble tamano) {
        this.tamano = tamano;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
}