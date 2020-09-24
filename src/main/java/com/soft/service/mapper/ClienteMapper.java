package com.soft.service.mapper;


import com.soft.domain.*;
import com.soft.service.dto.ClienteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cliente} and its DTO {@link ClienteDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ClienteMapper extends EntityMapper<ClienteDTO, Cliente> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.firstName", target = "userFirstName")
    ClienteDTO toDto(Cliente cliente);

    @Mapping(source = "userId", target = "user")
    Cliente toEntity(ClienteDTO clienteDTO);

    default Cliente fromId(Long id) {
        if (id == null) {
            return null;
        }
        Cliente cliente = new Cliente();
        cliente.setId(id);
        return cliente;
    }
}
