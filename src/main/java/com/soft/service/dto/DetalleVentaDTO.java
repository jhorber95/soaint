package com.soft.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.soft.domain.DetalleVenta} entity.
 */
public class DetalleVentaDTO implements Serializable {
    
    private Long id;


    private Long ventaId;

    private Long pruductoId;

    private String pruductoNombre;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVentaId() {
        return ventaId;
    }

    public void setVentaId(Long ventaId) {
        this.ventaId = ventaId;
    }

    public Long getPruductoId() {
        return pruductoId;
    }

    public void setPruductoId(Long productoId) {
        this.pruductoId = productoId;
    }

    public String getPruductoNombre() {
        return pruductoNombre;
    }

    public void setPruductoNombre(String productoNombre) {
        this.pruductoNombre = productoNombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DetalleVentaDTO)) {
            return false;
        }

        return id != null && id.equals(((DetalleVentaDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DetalleVentaDTO{" +
            "id=" + getId() +
            ", ventaId=" + getVentaId() +
            ", pruductoId=" + getPruductoId() +
            ", pruductoNombre='" + getPruductoNombre() + "'" +
            "}";
    }
}
