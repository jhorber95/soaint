package com.soft.web.rest;

import com.soft.SoaintApp;
import com.soft.domain.Venta;
import com.soft.domain.Cliente;
import com.soft.repository.VentaRepository;
import com.soft.service.VentaService;
import com.soft.service.dto.VentaDTO;
import com.soft.service.mapper.VentaMapper;
import com.soft.service.dto.VentaCriteria;
import com.soft.service.VentaQueryService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link VentaResource} REST controller.
 */
@SpringBootTest(classes = SoaintApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class VentaResourceIT {

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private VentaMapper ventaMapper;

    @Autowired
    private VentaService ventaService;

    @Autowired
    private VentaQueryService ventaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVentaMockMvc;

    private Venta venta;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Venta createEntity(EntityManager em) {
        Venta venta = new Venta()
            .fecha(DEFAULT_FECHA);
        // Add required entity
        Cliente cliente;
        if (TestUtil.findAll(em, Cliente.class).isEmpty()) {
            cliente = ClienteResourceIT.createEntity(em);
            em.persist(cliente);
            em.flush();
        } else {
            cliente = TestUtil.findAll(em, Cliente.class).get(0);
        }
        venta.setCliente(cliente);
        return venta;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Venta createUpdatedEntity(EntityManager em) {
        Venta venta = new Venta()
            .fecha(UPDATED_FECHA);
        // Add required entity
        Cliente cliente;
        if (TestUtil.findAll(em, Cliente.class).isEmpty()) {
            cliente = ClienteResourceIT.createUpdatedEntity(em);
            em.persist(cliente);
            em.flush();
        } else {
            cliente = TestUtil.findAll(em, Cliente.class).get(0);
        }
        venta.setCliente(cliente);
        return venta;
    }

    @BeforeEach
    public void initTest() {
        venta = createEntity(em);
    }

    @Test
    @Transactional
    public void createVenta() throws Exception {
        int databaseSizeBeforeCreate = ventaRepository.findAll().size();
        // Create the Venta
        VentaDTO ventaDTO = ventaMapper.toDto(venta);
        restVentaMockMvc.perform(post("/api/ventas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ventaDTO)))
            .andExpect(status().isCreated());

        // Validate the Venta in the database
        List<Venta> ventaList = ventaRepository.findAll();
        assertThat(ventaList).hasSize(databaseSizeBeforeCreate + 1);
        Venta testVenta = ventaList.get(ventaList.size() - 1);
        assertThat(testVenta.getFecha()).isEqualTo(DEFAULT_FECHA);
    }

    @Test
    @Transactional
    public void createVentaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ventaRepository.findAll().size();

        // Create the Venta with an existing ID
        venta.setId(1L);
        VentaDTO ventaDTO = ventaMapper.toDto(venta);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVentaMockMvc.perform(post("/api/ventas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ventaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Venta in the database
        List<Venta> ventaList = ventaRepository.findAll();
        assertThat(ventaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllVentas() throws Exception {
        // Initialize the database
        ventaRepository.saveAndFlush(venta);

        // Get all the ventaList
        restVentaMockMvc.perform(get("/api/ventas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(venta.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));
    }
    
    @Test
    @Transactional
    public void getVenta() throws Exception {
        // Initialize the database
        ventaRepository.saveAndFlush(venta);

        // Get the venta
        restVentaMockMvc.perform(get("/api/ventas/{id}", venta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(venta.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()));
    }


    @Test
    @Transactional
    public void getVentasByIdFiltering() throws Exception {
        // Initialize the database
        ventaRepository.saveAndFlush(venta);

        Long id = venta.getId();

        defaultVentaShouldBeFound("id.equals=" + id);
        defaultVentaShouldNotBeFound("id.notEquals=" + id);

        defaultVentaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVentaShouldNotBeFound("id.greaterThan=" + id);

        defaultVentaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVentaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllVentasByFechaIsEqualToSomething() throws Exception {
        // Initialize the database
        ventaRepository.saveAndFlush(venta);

        // Get all the ventaList where fecha equals to DEFAULT_FECHA
        defaultVentaShouldBeFound("fecha.equals=" + DEFAULT_FECHA);

        // Get all the ventaList where fecha equals to UPDATED_FECHA
        defaultVentaShouldNotBeFound("fecha.equals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllVentasByFechaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ventaRepository.saveAndFlush(venta);

        // Get all the ventaList where fecha not equals to DEFAULT_FECHA
        defaultVentaShouldNotBeFound("fecha.notEquals=" + DEFAULT_FECHA);

        // Get all the ventaList where fecha not equals to UPDATED_FECHA
        defaultVentaShouldBeFound("fecha.notEquals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllVentasByFechaIsInShouldWork() throws Exception {
        // Initialize the database
        ventaRepository.saveAndFlush(venta);

        // Get all the ventaList where fecha in DEFAULT_FECHA or UPDATED_FECHA
        defaultVentaShouldBeFound("fecha.in=" + DEFAULT_FECHA + "," + UPDATED_FECHA);

        // Get all the ventaList where fecha equals to UPDATED_FECHA
        defaultVentaShouldNotBeFound("fecha.in=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllVentasByFechaIsNullOrNotNull() throws Exception {
        // Initialize the database
        ventaRepository.saveAndFlush(venta);

        // Get all the ventaList where fecha is not null
        defaultVentaShouldBeFound("fecha.specified=true");

        // Get all the ventaList where fecha is null
        defaultVentaShouldNotBeFound("fecha.specified=false");
    }

    @Test
    @Transactional
    public void getAllVentasByClienteIsEqualToSomething() throws Exception {
        // Get already existing entity
        Cliente cliente = venta.getCliente();
        ventaRepository.saveAndFlush(venta);
        Long clienteId = cliente.getId();

        // Get all the ventaList where cliente equals to clienteId
        defaultVentaShouldBeFound("clienteId.equals=" + clienteId);

        // Get all the ventaList where cliente equals to clienteId + 1
        defaultVentaShouldNotBeFound("clienteId.equals=" + (clienteId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVentaShouldBeFound(String filter) throws Exception {
        restVentaMockMvc.perform(get("/api/ventas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(venta.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));

        // Check, that the count call also returns 1
        restVentaMockMvc.perform(get("/api/ventas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVentaShouldNotBeFound(String filter) throws Exception {
        restVentaMockMvc.perform(get("/api/ventas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVentaMockMvc.perform(get("/api/ventas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingVenta() throws Exception {
        // Get the venta
        restVentaMockMvc.perform(get("/api/ventas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVenta() throws Exception {
        // Initialize the database
        ventaRepository.saveAndFlush(venta);

        int databaseSizeBeforeUpdate = ventaRepository.findAll().size();

        // Update the venta
        Venta updatedVenta = ventaRepository.findById(venta.getId()).get();
        // Disconnect from session so that the updates on updatedVenta are not directly saved in db
        em.detach(updatedVenta);
        updatedVenta
            .fecha(UPDATED_FECHA);
        VentaDTO ventaDTO = ventaMapper.toDto(updatedVenta);

        restVentaMockMvc.perform(put("/api/ventas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ventaDTO)))
            .andExpect(status().isOk());

        // Validate the Venta in the database
        List<Venta> ventaList = ventaRepository.findAll();
        assertThat(ventaList).hasSize(databaseSizeBeforeUpdate);
        Venta testVenta = ventaList.get(ventaList.size() - 1);
        assertThat(testVenta.getFecha()).isEqualTo(UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void updateNonExistingVenta() throws Exception {
        int databaseSizeBeforeUpdate = ventaRepository.findAll().size();

        // Create the Venta
        VentaDTO ventaDTO = ventaMapper.toDto(venta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVentaMockMvc.perform(put("/api/ventas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ventaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Venta in the database
        List<Venta> ventaList = ventaRepository.findAll();
        assertThat(ventaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVenta() throws Exception {
        // Initialize the database
        ventaRepository.saveAndFlush(venta);

        int databaseSizeBeforeDelete = ventaRepository.findAll().size();

        // Delete the venta
        restVentaMockMvc.perform(delete("/api/ventas/{id}", venta.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Venta> ventaList = ventaRepository.findAll();
        assertThat(ventaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
