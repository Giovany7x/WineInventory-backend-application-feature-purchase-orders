package com.wineinventory.OrderOperationAndMonitoring.Domain.Services;

import com.wineinventory.OrderOperationAndMonitoring.Domain.Model.Aggregates.SalesOrder;
import com.wineinventory.OrderOperationAndMonitoring.Domain.Model.Queries.GetAllSalesOrdersByBuyerIdQuery;
import java.util.List;
import java.util.Optional;

/**
 * Contrato de la capa de dominio para las operaciones de lectura sobre las órdenes de venta.
 */
public interface SalesOrderQueryService {
    /** Ejecuta una consulta para obtener todas las órdenes de un comprador específico. */
    List<SalesOrder> handle(GetAllSalesOrdersByBuyerIdQuery query);

    /** Recupera una orden por su identificador, si existe. */
    Optional<SalesOrder> getById(Long orderId);

    /** Devuelve la lista completa de órdenes almacenadas. */
    List<SalesOrder> getAll();
}
