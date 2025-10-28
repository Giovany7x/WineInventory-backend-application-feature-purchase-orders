package com.wineinventory.OrderOperationAndMonitoring.Domain.Model.ValueObjects;

/**
 * Enumerates the states that a sales order can be in during its lifecycle.
 */
public enum OrderStatus {
    PENDING,
    PROCESSING,
    COMPLETED,
    CANCELLED
}
