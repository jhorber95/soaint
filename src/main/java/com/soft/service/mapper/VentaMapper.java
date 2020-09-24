package com.soft.service.mapper;


import com.soft.domain.*;
import com.soft.service.dto.VentaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Venta} and its DTO {@link VentaDTO}.
 */
@Mapper(componentModel = "spring", uses = {ClienteMapper.class})
public interface VentaMapper extends EntityMapper<VentaDTO, Venta> {

    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "cliente.nombre", target = "clienteNombre")
    VentaDTO toDto(Venta venta);

    @Mapping(source = "clienteId", target = "cliente")
    Venta toEntity(VentaDTO ventaDTO);

    default Venta fromId(Long id) {
        if (id == null) {
            return null;
        }
        Venta venta = new Venta();
        venta.setId(id);
        return venta;
    }
}
