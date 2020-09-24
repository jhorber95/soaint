package com.soft.repository;

import com.soft.domain.DetalleVenta;

import com.soft.domain.Venta;
import org.springframework.boot.loader.tools.LibraryScope;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the DetalleVenta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long>, JpaSpecificationExecutor<DetalleVenta> {

    List<DetalleVenta> findByVenta(Venta v);
}
