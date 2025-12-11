package cl.muebleria.api.dto;

import java.util.List;

public class CotizacionRequestDTO {

    private List<ItemCotizacionDTO> items;

  
    public CotizacionRequestDTO() {
    }

   
    public List<ItemCotizacionDTO> getItems() { return items; }
    public void setItems(List<ItemCotizacionDTO> items) { this.items = items; }
}