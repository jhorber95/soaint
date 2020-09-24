package com.soft.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A DetalleVenta.
 */
@Entity
@Table(name = "detalle_venta")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DetalleVenta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "detalleVentas", allowSetters = true)
    private Venta venta;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "detalleVentas", allowSetters = true)
    private Producto pruducto;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Venta getVenta() {
        return venta;
    }

    public DetalleVenta venta(Venta venta) {
        this.venta = venta;
        return this;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Producto getPruducto() {
        return pruducto;
    }

    public DetalleVenta pruducto(Producto producto) {
        this.pruducto = producto;
        return this;
    }

    public void setPruducto(Producto producto) {
        this.pruducto = producto;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DetalleVenta)) {
            return false;
        }
        return id != null && id.equals(((DetalleVenta) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DetalleVenta{" +
            "id=" + getId() +
            "}";
    }
}
