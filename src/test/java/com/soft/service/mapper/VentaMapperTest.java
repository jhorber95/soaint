package com.soft.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class VentaMapperTest {

    private VentaMapper ventaMapper;

    @BeforeEach
    public void setUp() {
        ventaMapper = new VentaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(ventaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(ventaMapper.fromId(null)).isNull();
    }
}
