package com.wineinventory.OrderOperationAndMonitoring.Domain.Services;

import com.wineinventory.OrderOperationAndMonitoring.Domain.Model.Aggregates.SalesOrder;
import com.wineinventory.OrderOperationAndMonitoring.Domain.Model.Commands.GenerateSalesOrderCommand;
import com.wineinventory.OrderOperationAndMonitoring.Domain.Model.ValueObjects.OrderStatus;

/**
 * Define las operaciones de escritura que la capa de aplicación expone para gestionar órdenes de venta.
 */
public interface SalesOrderCommandService {
    /** Procesa la creación de una nueva orden a partir de un comando. */
    SalesOrder handle(GenerateSalesOrderCommand command);

    /** Actualiza el estado de una orden existente y devuelve la entidad persistida. */
    SalesOrder updateStatus(Long orderId, OrderStatus newStatus);

    /** Elimina de forma permanente una orden identificada por su id. */
    void delete(Long orderId);
}
