package com.wineinventory.OrderOperationAndMonitoring.Domain.Model.Entities;

import com.wineinventory.OrderOperationAndMonitoring.Domain.Model.Aggregates.SalesOrder;
import com.wineinventory.shared.domain.model.entities.AuditableModel;
import com.wineinventory.shared.domain.model.valueobjects.Money;
import jakarta.persistence.*;
import java.util.Objects;

/**
 * Entidad que representa un ítem individual dentro de una orden de venta.
 * Mantiene la referencia al producto, la cantidad y los totales asociados.
 */
@Entity
@Table(name = "sales_order_items")
public class SalesOrderItem extends AuditableModel {

    /** Referencia al agregado padre que contiene el ítem. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_order_id", nullable = false)
    private SalesOrder salesOrder;

    /** Identificador del producto asociado al ítem. */
    @Column(nullable = false)
    private Long productId;

    /** Nombre descriptivo del producto. */
    @Column(nullable = false)
    private String productName;

    /** Cantidad solicitada del producto. */
    @Column(nullable = false)
    private Integer quantity;

    /** Precio unitario del producto con su moneda. */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "unit_price_amount", nullable = false)),
            @AttributeOverride(name = "currency", column = @Column(name = "unit_price_currency", nullable = false))
    })
    private Money unitPrice;

    /** Subtotal del ítem (precio unitario por cantidad). */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "line_total_amount", nullable = false)),
            @AttributeOverride(name = "currency", column = @Column(name = "line_total_currency", nullable = false))
    })
    private Money lineTotal;

    protected SalesOrderItem() {
        // Required by JPA
    }

    /**
     * Construye un ítem garantizando que los datos requeridos estén presentes y sean válidos.
     * El subtotal se calcula automáticamente en función de la cantidad y el precio unitario.
     */
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

    /**
     * Asociar el ítem con la orden correspondiente para mantener la integridad relacional.
     */
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
