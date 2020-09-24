package com.soft.web.rest;

import com.soft.SoaintApp;
import com.soft.domain.DetalleVenta;
import com.soft.domain.Venta;
import com.soft.domain.Producto;
import com.soft.repository.DetalleVentaRepository;
import com.soft.service.DetalleVentaService;
import com.soft.service.dto.DetalleVentaDTO;
import com.soft.service.mapper.DetalleVentaMapper;
import com.soft.service.dto.DetalleVentaCriteria;
import com.soft.service.DetalleVentaQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DetalleVentaResource} REST controller.
 */
@SpringBootTest(classes = SoaintApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DetalleVentaResourceIT {

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    @Autowired
    private DetalleVentaMapper detalleVentaMapper;

    @Autowired
    private DetalleVentaService detalleVentaService;

    @Autowired
    private DetalleVentaQueryService detalleVentaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDetalleVentaMockMvc;

    private DetalleVenta detalleVenta;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetalleVenta createEntity(EntityManager em) {
        DetalleVenta detalleVenta = new DetalleVenta();
        // Add required entity
        Venta venta;
        if (TestUtil.findAll(em, Venta.class).isEmpty()) {
            venta = VentaResourceIT.createEntity(em);
            em.persist(venta);
            em.flush();
        } else {
            venta = TestUtil.findAll(em, Venta.class).get(0);
        }
        detalleVenta.setVenta(venta);
        // Add required entity
        Producto producto;
        if (TestUtil.findAll(em, Producto.class).isEmpty()) {
            producto = ProductoResourceIT.createEntity(em);
            em.persist(producto);
            em.flush();
        } else {
            producto = TestUtil.findAll(em, Producto.class).get(0);
        }
        detalleVenta.setPruducto(producto);
        return detalleVenta;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetalleVenta createUpdatedEntity(EntityManager em) {
        DetalleVenta detalleVenta = new DetalleVenta();
        // Add required entity
        Venta venta;
        if (TestUtil.findAll(em, Venta.class).isEmpty()) {
            venta = VentaResourceIT.createUpdatedEntity(em);
            em.persist(venta);
            em.flush();
        } else {
            venta = TestUtil.findAll(em, Venta.class).get(0);
        }
        detalleVenta.setVenta(venta);
        // Add required entity
        Producto producto;
        if (TestUtil.findAll(em, Producto.class).isEmpty()) {
            producto = ProductoResourceIT.createUpdatedEntity(em);
            em.persist(producto);
            em.flush();
        } else {
            producto = TestUtil.findAll(em, Producto.class).get(0);
        }
        detalleVenta.setPruducto(producto);
        return detalleVenta;
    }

    @BeforeEach
    public void initTest() {
        detalleVenta = createEntity(em);
    }

    @Test
    @Transactional
    public void createDetalleVenta() throws Exception {
        int databaseSizeBeforeCreate = detalleVentaRepository.findAll().size();
        // Create the DetalleVenta
        DetalleVentaDTO detalleVentaDTO = detalleVentaMapper.toDto(detalleVenta);
        restDetalleVentaMockMvc.perform(post("/api/detalle-ventas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(detalleVentaDTO)))
            .andExpect(status().isCreated());

        // Validate the DetalleVenta in the database
        List<DetalleVenta> detalleVentaList = detalleVentaRepository.findAll();
        assertThat(detalleVentaList).hasSize(databaseSizeBeforeCreate + 1);
        DetalleVenta testDetalleVenta = detalleVentaList.get(detalleVentaList.size() - 1);
    }

    @Test
    @Transactional
    public void createDetalleVentaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = detalleVentaRepository.findAll().size();

        // Create the DetalleVenta with an existing ID
        detalleVenta.setId(1L);
        DetalleVentaDTO detalleVentaDTO = detalleVentaMapper.toDto(detalleVenta);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDetalleVentaMockMvc.perform(post("/api/detalle-ventas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(detalleVentaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DetalleVenta in the database
        List<DetalleVenta> detalleVentaList = detalleVentaRepository.findAll();
        assertThat(detalleVentaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDetalleVentas() throws Exception {
        // Initialize the database
        detalleVentaRepository.saveAndFlush(detalleVenta);

        // Get all the detalleVentaList
        restDetalleVentaMockMvc.perform(get("/api/detalle-ventas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detalleVenta.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getDetalleVenta() throws Exception {
        // Initialize the database
        detalleVentaRepository.saveAndFlush(detalleVenta);

        // Get the detalleVenta
        restDetalleVentaMockMvc.perform(get("/api/detalle-ventas/{id}", detalleVenta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(detalleVenta.getId().intValue()));
    }


    @Test
    @Transactional
    public void getDetalleVentasByIdFiltering() throws Exception {
        // Initialize the database
        detalleVentaRepository.saveAndFlush(detalleVenta);

        Long id = detalleVenta.getId();

        defaultDetalleVentaShouldBeFound("id.equals=" + id);
        defaultDetalleVentaShouldNotBeFound("id.notEquals=" + id);

        defaultDetalleVentaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDetalleVentaShouldNotBeFound("id.greaterThan=" + id);

        defaultDetalleVentaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDetalleVentaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDetalleVentasByVentaIsEqualToSomething() throws Exception {
        // Get already existing entity
        Venta venta = detalleVenta.getVenta();
        detalleVentaRepository.saveAndFlush(detalleVenta);
        Long ventaId = venta.getId();

        // Get all the detalleVentaList where venta equals to ventaId
        defaultDetalleVentaShouldBeFound("ventaId.equals=" + ventaId);

        // Get all the detalleVentaList where venta equals to ventaId + 1
        defaultDetalleVentaShouldNotBeFound("ventaId.equals=" + (ventaId + 1));
    }


    @Test
    @Transactional
    public void getAllDetalleVentasByPruductoIsEqualToSomething() throws Exception {
        // Get already existing entity
        Producto pruducto = detalleVenta.getPruducto();
        detalleVentaRepository.saveAndFlush(detalleVenta);
        Long pruductoId = pruducto.getId();

        // Get all the detalleVentaList where pruducto equals to pruductoId
        defaultDetalleVentaShouldBeFound("pruductoId.equals=" + pruductoId);

        // Get all the detalleVentaList where pruducto equals to pruductoId + 1
        defaultDetalleVentaShouldNotBeFound("pruductoId.equals=" + (pruductoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDetalleVentaShouldBeFound(String filter) throws Exception {
        restDetalleVentaMockMvc.perform(get("/api/detalle-ventas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detalleVenta.getId().intValue())));

        // Check, that the count call also returns 1
        restDetalleVentaMockMvc.perform(get("/api/detalle-ventas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDetalleVentaShouldNotBeFound(String filter) throws Exception {
        restDetalleVentaMockMvc.perform(get("/api/detalle-ventas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDetalleVentaMockMvc.perform(get("/api/detalle-ventas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingDetalleVenta() throws Exception {
        // Get the detalleVenta
        restDetalleVentaMockMvc.perform(get("/api/detalle-ventas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDetalleVenta() throws Exception {
        // Initialize the database
        detalleVentaRepository.saveAndFlush(detalleVenta);

        int databaseSizeBeforeUpdate = detalleVentaRepository.findAll().size();

        // Update the detalleVenta
        DetalleVenta updatedDetalleVenta = detalleVentaRepository.findById(detalleVenta.getId()).get();
        // Disconnect from session so that the updates on updatedDetalleVenta are not directly saved in db
        em.detach(updatedDetalleVenta);
        DetalleVentaDTO detalleVentaDTO = detalleVentaMapper.toDto(updatedDetalleVenta);

        restDetalleVentaMockMvc.perform(put("/api/detalle-ventas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(detalleVentaDTO)))
            .andExpect(status().isOk());

        // Validate the DetalleVenta in the database
        List<DetalleVenta> detalleVentaList = detalleVentaRepository.findAll();
        assertThat(detalleVentaList).hasSize(databaseSizeBeforeUpdate);
        DetalleVenta testDetalleVenta = detalleVentaList.get(detalleVentaList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingDetalleVenta() throws Exception {
        int databaseSizeBeforeUpdate = detalleVentaRepository.findAll().size();

        // Create the DetalleVenta
        DetalleVentaDTO detalleVentaDTO = detalleVentaMapper.toDto(detalleVenta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetalleVentaMockMvc.perform(put("/api/detalle-ventas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(detalleVentaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DetalleVenta in the database
        List<DetalleVenta> detalleVentaList = detalleVentaRepository.findAll();
        assertThat(detalleVentaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDetalleVenta() throws Exception {
        // Initialize the database
        detalleVentaRepository.saveAndFlush(detalleVenta);

        int databaseSizeBeforeDelete = detalleVentaRepository.findAll().size();

        // Delete the detalleVenta
        restDetalleVentaMockMvc.perform(delete("/api/detalle-ventas/{id}", detalleVenta.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DetalleVenta> detalleVentaList = detalleVentaRepository.findAll();
        assertThat(detalleVentaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
