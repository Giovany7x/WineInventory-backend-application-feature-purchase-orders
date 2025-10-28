package com.wineinventory.OrderOperationAndMonitoring.Application.internal.queryservices;

import com.wineinventory.OrderOperationAndMonitoring.Domain.Model.Aggregates.SalesOrder;
import com.wineinventory.OrderOperationAndMonitoring.Domain.Model.Queries.GetAllSalesOrdersByBuyerIdQuery;
import com.wineinventory.OrderOperationAndMonitoring.Domain.Repositories.SalesOrderRepository;
import com.wineinventory.OrderOperationAndMonitoring.Domain.Services.SalesOrderQueryService;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación de las operaciones de lectura sobre órdenes de venta.
 * Se declara transaccional de solo lectura para optimizar el acceso a datos.
 */
@Service
@Transactional(readOnly = true)
public class SalesOrderQueryServiceImpl implements SalesOrderQueryService {

    private final SalesOrderRepository salesOrderRepository;

    public SalesOrderQueryServiceImpl(SalesOrderRepository salesOrderRepository) {
        this.salesOrderRepository = salesOrderRepository;
    }

    @Override
    public List<SalesOrder> handle(GetAllSalesOrdersByBuyerIdQuery query) {
        // Delegamos en el repositorio la recuperación filtrada por comprador.
        return salesOrderRepository.findAllByBuyerId(query.buyerId());
    }

    @Override
    public Optional<SalesOrder> getById(Long orderId) {
        // Retorna un Optional para manejar la ausencia de resultados en capas superiores.
        return salesOrderRepository.findById(orderId);
    }

    @Override
    public List<SalesOrder> getAll() {
        // Utiliza el método estándar de Spring Data para traer todas las órdenes.
        return salesOrderRepository.findAll();
    }
}
