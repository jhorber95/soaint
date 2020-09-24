package com.soft.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.soft.web.rest.TestUtil;

public class VentaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VentaDTO.class);
        VentaDTO ventaDTO1 = new VentaDTO();
        ventaDTO1.setId(1L);
        VentaDTO ventaDTO2 = new VentaDTO();
        assertThat(ventaDTO1).isNotEqualTo(ventaDTO2);
        ventaDTO2.setId(ventaDTO1.getId());
        assertThat(ventaDTO1).isEqualTo(ventaDTO2);
        ventaDTO2.setId(2L);
        assertThat(ventaDTO1).isNotEqualTo(ventaDTO2);
        ventaDTO1.setId(null);
        assertThat(ventaDTO1).isNotEqualTo(ventaDTO2);
    }
}
