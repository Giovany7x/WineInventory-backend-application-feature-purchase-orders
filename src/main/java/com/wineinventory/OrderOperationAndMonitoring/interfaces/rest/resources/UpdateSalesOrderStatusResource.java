package com.wineinventory.OrderOperationAndMonitoring.interfaces.rest.resources;

import com.wineinventory.OrderOperationAndMonitoring.Domain.Model.ValueObjects.OrderStatus;
import jakarta.validation.constraints.NotNull;

/**
 * Recurso utilizado para solicitar un cambio de estado sobre una orden existente.
 */
public record UpdateSalesOrderStatusResource(@NotNull OrderStatus status) {
}
