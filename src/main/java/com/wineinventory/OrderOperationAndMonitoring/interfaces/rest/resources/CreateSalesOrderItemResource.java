package com.wineinventory.OrderOperationAndMonitoring.interfaces.rest.resources;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Describe cada línea de detalle que llega en la creación de una orden desde la API.
 * Las anotaciones de validación ayudan a rechazar solicitudes incompletas o inválidas.
 */
public record CreateSalesOrderItemResource(
        @NotNull Long productId,
        @NotBlank String productName,
        @NotNull @Positive Integer quantity,
        @NotNull @DecimalMin(value = "0.0", inclusive = false) Double unitPrice
) {
}
