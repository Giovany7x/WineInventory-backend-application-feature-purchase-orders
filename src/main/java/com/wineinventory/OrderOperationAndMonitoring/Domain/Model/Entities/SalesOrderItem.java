package com.wineinventory.OrderOperationAndMonitoring.Domain.Model.Entities;

import com.wineinventory.OrderOperationAndMonitoring.Domain.Model.Aggregates.SalesOrder;
import com.wineinventory.shared.domain.model.entities.AuditableModel;
import com.wineinventory.shared.domain.model.valueobjects.Money;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "sales_order_items")
public class SalesOrderItem extends AuditableModel {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_order_id", nullable = false)
    private SalesOrder salesOrder;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private Integer quantity;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "unit_price_amount", nullable = false)),
            @AttributeOverride(name = "currency", column = @Column(name = "unit_price_currency", nullable = false))
    })
    private Money unitPrice;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "line_total_amount", nullable = false)),
            @AttributeOverride(name = "currency", column = @Column(name = "line_total_currency", nullable = false))
    })
    private Money lineTotal;

    protected SalesOrderItem() {
        // Required by JPA
    }

    public SalesOrderItem(Long productId, String productName, Integer quantity, Money unitPrice) {
        this.productId = Objects.requireNonNull(productId, "The product identifier is required");
        this.productName = Objects.requireNonNull(productName, "The product name is required");
        this.quantity = Objects.requireNonNull(quantity, "The quantity is required");
        if (quantity <= 0) {
            throw new IllegalArgumentException("The quantity must be greater than zero");
        }
        this.unitPrice = Objects.requireNonNull(unitPrice, "The unit price is required");
        this.lineTotal = Money.of(unitPrice.amount() * quantity, unitPrice.currency());
    }

    public void assignOrder(SalesOrder order) {
        this.salesOrder = order;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Money getUnitPrice() {
        return unitPrice;
    }

    public Money getLineTotal() {
        return lineTotal;
    }
}
