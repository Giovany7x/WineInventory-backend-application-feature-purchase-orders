package com.wineinventory.OrderOperationAndMonitoring.Domain.Model.Queries;

/**
 * Consulta sencilla que modela la intención de recuperar todas las órdenes de un comprador
 * específico para la capa de aplicación.
 */
public record GetAllSalesOrdersByBuyerIdQuery(Long buyerId) {
}
