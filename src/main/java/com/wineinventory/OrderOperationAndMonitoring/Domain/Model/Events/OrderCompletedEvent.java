package com.wineinventory.OrderOperationAndMonitoring.Domain.Model.Events;

import java.time.LocalDateTime;

/**
 * Domain event emitted when a sales order transitions to the COMPLETED status.
 */
public record OrderCompletedEvent(Long orderId, String orderNumber, LocalDateTime completedAt) {
}
