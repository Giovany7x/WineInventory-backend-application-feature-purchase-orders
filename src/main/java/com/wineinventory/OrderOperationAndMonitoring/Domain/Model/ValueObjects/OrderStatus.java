package com.wineinventory.OrderOperationAndMonitoring.Domain.Model.ValueObjects;

/**
 * Enumera los posibles estados del ciclo de vida de una orden de venta.
 */
public enum OrderStatus {
    PENDING,
    PROCESSING,
    COMPLETED,
    CANCELLED
}
