package com.soft.web.rest;

import com.soft.domain.DetalleVenta;
import com.soft.service.DetalleVentaService;
import com.soft.service.mapper.DetalleVentaMapper;
import com.soft.web.rest.errors.BadRequestAlertException;
import com.soft.service.dto.DetalleVentaDTO;
import com.soft.service.dto.DetalleVentaCriteria;
import com.soft.service.DetalleVentaQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rx.Single;
import rx.schedulers.Schedulers;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing {@link com.soft.domain.DetalleVenta}.
 */
@RestController
@RequestMapping("/api")
public class DetalleVentaResource {

    private final Logger log = LoggerFactory.getLogger(DetalleVentaResource.class);

    private static final String ENTITY_NAME = "detalleVenta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DetalleVentaService detalleVentaService;

    private final DetalleVentaQueryService detalleVentaQueryService;

    private final DetalleVentaMapper detalleVentaMapper;

    public DetalleVentaResource(DetalleVentaService detalleVentaService, DetalleVentaQueryService detalleVentaQueryService, DetalleVentaMapper detalleVentaMapper) {
        this.detalleVentaService = detalleVentaService;
        this.detalleVentaQueryService = detalleVentaQueryService;
        this.detalleVentaMapper = detalleVentaMapper;
    }

    /**
     * {@code POST  /detalle-ventas} : Create a new detalleVenta.
     *
     * @param detalleVentaDTO the detalleVentaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new detalleVentaDTO, or with status {@code 400 (Bad Request)} if the detalleVenta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/detalle-ventas")
    public ResponseEntity<DetalleVentaDTO> createDetalleVenta(@Valid @RequestBody DetalleVentaDTO detalleVentaDTO) throws URISyntaxException {
        log.debug("REST request to save DetalleVenta : {}", detalleVentaDTO);
        if (detalleVentaDTO.getId() != null) {
            throw new BadRequestAlertException("A new detalleVenta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DetalleVentaDTO result = detalleVentaService.save(detalleVentaDTO);
        return ResponseEntity.created(new URI("/api/detalle-ventas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /detalle-ventas} : Updates an existing detalleVenta.
     *
     * @param detalleVentaDTO the detalleVentaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detalleVentaDTO,
     * or with status {@code 400 (Bad Request)} if the detalleVentaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the detalleVentaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/detalle-ventas")
    public ResponseEntity<DetalleVentaDTO> updateDetalleVenta(@Valid @RequestBody DetalleVentaDTO detalleVentaDTO) throws URISyntaxException {
        log.debug("REST request to update DetalleVenta : {}", detalleVentaDTO);
        if (detalleVentaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DetalleVentaDTO result = detalleVentaService.save(detalleVentaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, detalleVentaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /detalle-ventas} : get all the detalleVentas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of detalleVentas in body.
     */
    @GetMapping("/detalle-ventas")
    public ResponseEntity<List<DetalleVentaDTO>> getAllDetalleVentas(DetalleVentaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DetalleVentas by criteria: {}", criteria);
        Page<DetalleVentaDTO> page = detalleVentaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /detalle-ventas/count} : count all the detalleVentas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/detalle-ventas/count")
    public ResponseEntity<Long> countDetalleVentas(DetalleVentaCriteria criteria) {
        log.debug("REST request to count DetalleVentas by criteria: {}", criteria);
        return ResponseEntity.ok().body(detalleVentaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /detalle-ventas/:id} : get the "id" detalleVenta.
     *
     * @param id the id of the detalleVentaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the detalleVentaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/detalle-ventas/{id}")
    public ResponseEntity<DetalleVentaDTO> getDetalleVenta(@PathVariable Long id) {
        log.debug("REST request to get DetalleVenta : {}", id);
        Optional<DetalleVentaDTO> detalleVentaDTO = detalleVentaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(detalleVentaDTO);
    }

    /**
     * {@code DELETE  /detalle-ventas/:id} : delete the "id" detalleVenta.
     *
     * @param id the id of the detalleVentaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/detalle-ventas/{id}")
    public ResponseEntity<Void> deleteDetalleVenta(@PathVariable Long id) {
        log.debug("REST request to delete DetalleVenta : {}", id);
        detalleVentaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/detalle-ventas/rx/{idVenta}")
    public Single<ResponseEntity<BaseWebResponse<List<DetalleVentaDTO>>>> get(@PathVariable Long idVenta) {

        return detalleVentaService.getDetail(idVenta).subscribeOn(Schedulers.io())
            .map(response -> ResponseEntity.ok(BaseWebResponse.successWithData(toList(response))));
    }

    private List<DetalleVentaDTO> toList(List<DetalleVenta> data) {
        return data
            .stream()
            .map(detalleVentaMapper::toDto)
            .collect(Collectors.toList());
    }

}
