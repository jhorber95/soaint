package com.soft.service.mapper;


import com.soft.domain.*;
import com.soft.service.dto.DetalleVentaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DetalleVenta} and its DTO {@link DetalleVentaDTO}.
 */
@Mapper(componentModel = "spring", uses = {VentaMapper.class, ProductoMapper.class})
public interface DetalleVentaMapper extends EntityMapper<DetalleVentaDTO, DetalleVenta> {

    @Mapping(source = "venta.id", target = "ventaId")
    @Mapping(source = "pruducto.id", target = "pruductoId")
    @Mapping(source = "pruducto.nombre", target = "pruductoNombre")
    DetalleVentaDTO toDto(DetalleVenta detalleVenta);

    @Mapping(source = "ventaId", target = "venta")
    @Mapping(source = "pruductoId", target = "pruducto")
    DetalleVenta toEntity(DetalleVentaDTO detalleVentaDTO);

    default DetalleVenta fromId(Long id) {
        if (id == null) {
            return null;
        }
        DetalleVenta detalleVenta = new DetalleVenta();
        detalleVenta.setId(id);
        return detalleVenta;
    }
}
