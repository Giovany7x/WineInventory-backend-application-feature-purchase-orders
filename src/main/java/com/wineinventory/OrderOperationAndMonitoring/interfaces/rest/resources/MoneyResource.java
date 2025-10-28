package com.wineinventory.OrderOperationAndMonitoring.interfaces.rest.resources;

/**
 * Representa cantidades monetarias expuestas o recibidas por la API.
 */
public record MoneyResource(Double amount, String currency) {
}
