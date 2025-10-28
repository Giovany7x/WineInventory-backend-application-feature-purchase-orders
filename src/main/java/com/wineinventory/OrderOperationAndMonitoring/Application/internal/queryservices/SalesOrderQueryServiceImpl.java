package com.wineinventory.OrderOperationAndMonitoring.Application.internal.queryservices;

import com.wineinventory.OrderOperationAndMonitoring.Domain.Model.Aggregates.SalesOrder;
import com.wineinventory.OrderOperationAndMonitoring.Domain.Model.Queries.GetAllSalesOrdersByBuyerIdQuery;
import com.wineinventory.OrderOperationAndMonitoring.Domain.Repositories.SalesOrderRepository;
import com.wineinventory.OrderOperationAndMonitoring.Domain.Services.SalesOrderQueryService;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SalesOrderQueryServiceImpl implements SalesOrderQueryService {

    private final SalesOrderRepository salesOrderRepository;

    public SalesOrderQueryServiceImpl(SalesOrderRepository salesOrderRepository) {
        this.salesOrderRepository = salesOrderRepository;
    }

    @Override
    public List<SalesOrder> handle(GetAllSalesOrdersByBuyerIdQuery query) {
        return salesOrderRepository.findAllByBuyerId(query.buyerId());
    }

    @Override
    public Optional<SalesOrder> getById(Long orderId) {
        return salesOrderRepository.findById(orderId);
    }

    @Override
    public List<SalesOrder> getAll() {
        return salesOrderRepository.findAll();
    }
}
