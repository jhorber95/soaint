package com.soft.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DetalleVentaMapperTest {

    private DetalleVentaMapper detalleVentaMapper;

    @BeforeEach
    public void setUp() {
        detalleVentaMapper = new DetalleVentaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(detalleVentaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(detalleVentaMapper.fromId(null)).isNull();
    }
}
