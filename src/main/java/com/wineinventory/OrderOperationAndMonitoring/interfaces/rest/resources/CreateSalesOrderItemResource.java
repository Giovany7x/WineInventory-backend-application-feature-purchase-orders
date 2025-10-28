package com.wineinventory.OrderOperationAndMonitoring.interfaces.rest.resources;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateSalesOrderItemResource(
        @NotNull Long productId,
        @NotBlank String productName,
        @NotNull @Positive Integer quantity,
        @NotNull @DecimalMin(value = "0.0", inclusive = false) Double unitPrice
) {
}
