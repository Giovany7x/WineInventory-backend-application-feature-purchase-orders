package com.wineinventory.OrderOperationAndMonitoring.Domain.Repositories;

import com.wineinventory.OrderOperationAndMonitoring.Domain.Model.Aggregates.SalesOrder;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio Spring Data encargado de persistir y recuperar agregados de órdenes de venta.
 */
@Repository
public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {
    /** Obtiene todas las órdenes que pertenecen a un comprador específico. */
    List<SalesOrder> findAllByBuyerId(Long buyerId);

    /** Busca una orden por su número legible, útil para integraciones externas. */
    Optional<SalesOrder> findByOrderNumber(String orderNumber);
}
