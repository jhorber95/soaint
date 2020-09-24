package com.soft.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.soft.domain.DetalleVenta} entity. This class is used
 * in {@link com.soft.web.rest.DetalleVentaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /detalle-ventas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DetalleVentaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter ventaId;

    private LongFilter pruductoId;

    public DetalleVentaCriteria() {
    }

    public DetalleVentaCriteria(DetalleVentaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.ventaId = other.ventaId == null ? null : other.ventaId.copy();
        this.pruductoId = other.pruductoId == null ? null : other.pruductoId.copy();
    }

    @Override
    public DetalleVentaCriteria copy() {
        return new DetalleVentaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getVentaId() {
        return ventaId;
    }

    public void setVentaId(LongFilter ventaId) {
        this.ventaId = ventaId;
    }

    public LongFilter getPruductoId() {
        return pruductoId;
    }

    public void setPruductoId(LongFilter pruductoId) {
        this.pruductoId = pruductoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DetalleVentaCriteria that = (DetalleVentaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(ventaId, that.ventaId) &&
            Objects.equals(pruductoId, that.pruductoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        ventaId,
        pruductoId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DetalleVentaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (ventaId != null ? "ventaId=" + ventaId + ", " : "") +
                (pruductoId != null ? "pruductoId=" + pruductoId + ", " : "") +
            "}";
    }

}
