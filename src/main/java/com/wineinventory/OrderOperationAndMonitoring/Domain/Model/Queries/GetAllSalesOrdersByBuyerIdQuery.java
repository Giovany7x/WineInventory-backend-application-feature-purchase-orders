package com.wineinventory.OrderOperationAndMonitoring.Domain.Model.Queries;

/**
 * Query that retrieves all the sales orders associated with a buyer.
 */
public record GetAllSalesOrdersByBuyerIdQuery(Long buyerId) {
}
