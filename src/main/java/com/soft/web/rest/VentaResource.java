package com.soft.web.rest;

import com.soft.service.VentaService;
import com.soft.web.rest.errors.BadRequestAlertException;
import com.soft.service.dto.VentaDTO;
import com.soft.service.dto.VentaCriteria;
import com.soft.service.VentaQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.soft.domain.Venta}.
 */
@RestController
@RequestMapping("/api")
public class VentaResource {

    private final Logger log = LoggerFactory.getLogger(VentaResource.class);

    private static final String ENTITY_NAME = "venta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VentaService ventaService;

    private final VentaQueryService ventaQueryService;

    public VentaResource(VentaService ventaService, VentaQueryService ventaQueryService) {
        this.ventaService = ventaService;
        this.ventaQueryService = ventaQueryService;
    }

    /**
     * {@code POST  /ventas} : Create a new venta.
     *
     * @param ventaDTO the ventaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ventaDTO, or with status {@code 400 (Bad Request)} if the venta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ventas")
    public ResponseEntity<VentaDTO> createVenta(@Valid @RequestBody VentaDTO ventaDTO) throws URISyntaxException {
        log.debug("REST request to save Venta : {}", ventaDTO);
        if (ventaDTO.getId() != null) {
            throw new BadRequestAlertException("A new venta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VentaDTO result = ventaService.save(ventaDTO);
        return ResponseEntity.created(new URI("/api/ventas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ventas} : Updates an existing venta.
     *
     * @param ventaDTO the ventaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ventaDTO,
     * or with status {@code 400 (Bad Request)} if the ventaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ventaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ventas")
    public ResponseEntity<VentaDTO> updateVenta(@Valid @RequestBody VentaDTO ventaDTO) throws URISyntaxException {
        log.debug("REST request to update Venta : {}", ventaDTO);
        if (ventaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VentaDTO result = ventaService.save(ventaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ventaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ventas} : get all the ventas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ventas in body.
     */
    @GetMapping("/ventas")
    public ResponseEntity<List<VentaDTO>> getAllVentas(VentaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Ventas by criteria: {}", criteria);
        Page<VentaDTO> page = ventaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ventas/count} : count all the ventas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ventas/count")
    public ResponseEntity<Long> countVentas(VentaCriteria criteria) {
        log.debug("REST request to count Ventas by criteria: {}", criteria);
        return ResponseEntity.ok().body(ventaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ventas/:id} : get the "id" venta.
     *
     * @param id the id of the ventaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ventaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ventas/{id}")
    public ResponseEntity<VentaDTO> getVenta(@PathVariable Long id) {
        log.debug("REST request to get Venta : {}", id);
        Optional<VentaDTO> ventaDTO = ventaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ventaDTO);
    }

    /**
     * {@code DELETE  /ventas/:id} : delete the "id" venta.
     *
     * @param id the id of the ventaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ventas/{id}")
    public ResponseEntity<Void> deleteVenta(@PathVariable Long id) {
        log.debug("REST request to delete Venta : {}", id);
        ventaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
