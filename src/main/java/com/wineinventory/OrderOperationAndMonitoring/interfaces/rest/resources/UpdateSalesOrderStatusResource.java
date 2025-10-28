package com.wineinventory.OrderOperationAndMonitoring.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

/**
 * Recurso utilizado para solicitar un cambio de estado sobre una orden existente.
 */
public record UpdateSalesOrderStatusResource(@NotBlank String status) {
}
