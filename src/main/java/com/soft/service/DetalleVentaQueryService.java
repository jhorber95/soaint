package com.soft.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.soft.domain.DetalleVenta;
import com.soft.domain.*; // for static metamodels
import com.soft.repository.DetalleVentaRepository;
import com.soft.service.dto.DetalleVentaCriteria;
import com.soft.service.dto.DetalleVentaDTO;
import com.soft.service.mapper.DetalleVentaMapper;

/**
 * Service for executing complex queries for {@link DetalleVenta} entities in the database.
 * The main input is a {@link DetalleVentaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DetalleVentaDTO} or a {@link Page} of {@link DetalleVentaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DetalleVentaQueryService extends QueryService<DetalleVenta> {

    private final Logger log = LoggerFactory.getLogger(DetalleVentaQueryService.class);

    private final DetalleVentaRepository detalleVentaRepository;

    private final DetalleVentaMapper detalleVentaMapper;

    public DetalleVentaQueryService(DetalleVentaRepository detalleVentaRepository, DetalleVentaMapper detalleVentaMapper) {
        this.detalleVentaRepository = detalleVentaRepository;
        this.detalleVentaMapper = detalleVentaMapper;
    }

    /**
     * Return a {@link List} of {@link DetalleVentaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DetalleVentaDTO> findByCriteria(DetalleVentaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DetalleVenta> specification = createSpecification(criteria);
        return detalleVentaMapper.toDto(detalleVentaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DetalleVentaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DetalleVentaDTO> findByCriteria(DetalleVentaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DetalleVenta> specification = createSpecification(criteria);
        return detalleVentaRepository.findAll(specification, page)
            .map(detalleVentaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DetalleVentaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DetalleVenta> specification = createSpecification(criteria);
        return detalleVentaRepository.count(specification);
    }

    /**
     * Function to convert {@link DetalleVentaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DetalleVenta> createSpecification(DetalleVentaCriteria criteria) {
        Specification<DetalleVenta> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DetalleVenta_.id));
            }
            if (criteria.getVentaId() != null) {
                specification = specification.and(buildSpecification(criteria.getVentaId(),
                    root -> root.join(DetalleVenta_.venta, JoinType.LEFT).get(Venta_.id)));
            }
            if (criteria.getPruductoId() != null) {
                specification = specification.and(buildSpecification(criteria.getPruductoId(),
                    root -> root.join(DetalleVenta_.pruducto, JoinType.LEFT).get(Producto_.id)));
            }
        }
        return specification;
    }
}
