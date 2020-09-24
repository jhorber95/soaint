package com.soft.service;

import com.soft.domain.DetalleVenta;
import com.soft.repository.DetalleVentaRepository;
import com.soft.service.dto.DetalleVentaDTO;
import com.soft.service.mapper.DetalleVentaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DetalleVenta}.
 */
@Service
@Transactional
public class DetalleVentaService {

    private final Logger log = LoggerFactory.getLogger(DetalleVentaService.class);

    private final DetalleVentaRepository detalleVentaRepository;

    private final DetalleVentaMapper detalleVentaMapper;

    public DetalleVentaService(DetalleVentaRepository detalleVentaRepository, DetalleVentaMapper detalleVentaMapper) {
        this.detalleVentaRepository = detalleVentaRepository;
        this.detalleVentaMapper = detalleVentaMapper;
    }

    /**
     * Save a detalleVenta.
     *
     * @param detalleVentaDTO the entity to save.
     * @return the persisted entity.
     */
    public DetalleVentaDTO save(DetalleVentaDTO detalleVentaDTO) {
        log.debug("Request to save DetalleVenta : {}", detalleVentaDTO);
        DetalleVenta detalleVenta = detalleVentaMapper.toEntity(detalleVentaDTO);
        detalleVenta = detalleVentaRepository.save(detalleVenta);
        return detalleVentaMapper.toDto(detalleVenta);
    }

    /**
     * Get all the detalleVentas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DetalleVentaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DetalleVentas");
        return detalleVentaRepository.findAll(pageable)
            .map(detalleVentaMapper::toDto);
    }


    /**
     * Get one detalleVenta by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DetalleVentaDTO> findOne(Long id) {
        log.debug("Request to get DetalleVenta : {}", id);
        return detalleVentaRepository.findById(id)
            .map(detalleVentaMapper::toDto);
    }

    /**
     * Delete the detalleVenta by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DetalleVenta : {}", id);
        detalleVentaRepository.deleteById(id);
    }
}
