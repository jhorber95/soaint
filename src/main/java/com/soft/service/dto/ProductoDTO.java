package com.soft.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.soft.domain.Producto} entity.
 */
public class ProductoDTO implements Serializable {
    
    private Long id;

    private String nombre;

    private Double precion;

    
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

    public Double getPrecion() {
        return precion;
    }

    public void setPrecion(Double precion) {
        this.precion = precion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductoDTO)) {
            return false;
        }

        return id != null && id.equals(((ProductoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductoDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", precion=" + getPrecion() +
            "}";
    }
}
