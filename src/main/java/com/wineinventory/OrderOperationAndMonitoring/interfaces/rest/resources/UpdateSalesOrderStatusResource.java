package com.wineinventory.OrderOperationAndMonitoring.interfaces.rest.resources;

import com.wineinventory.OrderOperationAndMonitoring.Domain.Model.ValueObjects.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateSalesOrderStatusResource(@NotNull OrderStatus status) {
}
