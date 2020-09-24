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
 * Criteria class for the {@link com.soft.domain.Cliente} entity. This class is used
 * in {@link com.soft.web.rest.ClienteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /clientes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ClienteCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nombre;

    private StringFilter apellido;

    private StringFilter dni;

    private StringFilter telefono;

    private StringFilter email;

    private LongFilter userId;

    public ClienteCriteria() {
    }

    public ClienteCriteria(ClienteCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nombre = other.nombre == null ? null : other.nombre.copy();
        this.apellido = other.apellido == null ? null : other.apellido.copy();
        this.dni = other.dni == null ? null : other.dni.copy();
        this.telefono = other.telefono == null ? null : other.telefono.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
    }

    @Override
    public ClienteCriteria copy() {
        return new ClienteCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNombre() {
        return nombre;
    }

    public void setNombre(StringFilter nombre) {
        this.nombre = nombre;
    }

    public StringFilter getApellido() {
        return apellido;
    }

    public void setApellido(StringFilter apellido) {
        this.apellido = apellido;
    }

    public StringFilter getDni() {
        return dni;
    }

    public void setDni(StringFilter dni) {
        this.dni = dni;
    }

    public StringFilter getTelefono() {
        return telefono;
    }

    public void setTelefono(StringFilter telefono) {
        this.telefono = telefono;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ClienteCriteria that = (ClienteCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nombre, that.nombre) &&
            Objects.equals(apellido, that.apellido) &&
            Objects.equals(dni, that.dni) &&
            Objects.equals(telefono, that.telefono) &&
            Objects.equals(email, that.email) &&
            Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nombre,
        apellido,
        dni,
        telefono,
        email,
        userId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClienteCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nombre != null ? "nombre=" + nombre + ", " : "") +
                (apellido != null ? "apellido=" + apellido + ", " : "") +
                (dni != null ? "dni=" + dni + ", " : "") +
                (telefono != null ? "telefono=" + telefono + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
            "}";
    }

}
