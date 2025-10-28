package com.wineinventory.OrderOperationAndMonitoring.Domain.Model.Aggregates;

import com.wineinventory.OrderOperationAndMonitoring.Domain.Model.Entities.SalesOrderItem;
import com.wineinventory.OrderOperationAndMonitoring.Domain.Model.Events.OrderCompletedEvent;
import com.wineinventory.OrderOperationAndMonitoring.Domain.Model.ValueObjects.DeliveryInformation;
import com.wineinventory.OrderOperationAndMonitoring.Domain.Model.ValueObjects.OrderStatus;
import com.wineinventory.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.wineinventory.shared.domain.model.valueobjects.Money;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "sales_orders")
public class SalesOrder extends AuditableAbstractAggregateRoot<SalesOrder> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long buyerId;

    @Column(nullable = false, unique = true)
    private String orderNumber;

    @Column(nullable = false)
    private LocalDateTime orderedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Embedded
    private DeliveryInformation deliveryInformation;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "total_amount", nullable = false)),
            @AttributeOverride(name = "currency", column = @Column(name = "total_currency", nullable = false))
    })
    private Money totalAmount;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @OneToMany(mappedBy = "salesOrder", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<SalesOrderItem> items = new ArrayList<>();

    protected SalesOrder() {
        // Required by JPA
    }

    private SalesOrder(Long buyerId, String currency, DeliveryInformation deliveryInformation, String notes) {
        this.buyerId = Objects.requireNonNull(buyerId, "The buyer identifier is required");
        this.deliveryInformation = Objects.requireNonNull(deliveryInformation, "Delivery information is required");
        this.notes = notes;
        this.status = OrderStatus.PENDING;
        this.orderNumber = generateOrderNumber();
        this.orderedAt = LocalDateTime.now();
        this.totalAmount = Money.of(0.0, Objects.requireNonNull(currency, "Currency is required"));
    }

    public static SalesOrder create(Long buyerId, String currency, DeliveryInformation deliveryInformation, String notes) {
        return new SalesOrder(buyerId, currency, deliveryInformation, notes);
    }

    private String generateOrderNumber() {
        return "SO-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(Locale.ROOT);
    }

    public void addItem(SalesOrderItem item) {
        Objects.requireNonNull(item, "The item to add cannot be null");
        if (!item.getUnitPrice().currency().equalsIgnoreCase(this.totalAmount.currency())) {
            throw new IllegalArgumentException("All order items must share the same currency");
        }
        item.assignOrder(this);
        this.items.add(item);
        recalculateTotals();
    }

    public void updateStatus(OrderStatus newStatus) {
        Objects.requireNonNull(newStatus, "Order status is required");
        this.status = newStatus;
        if (OrderStatus.COMPLETED.equals(newStatus)) {
            this.addDomainEvent(new OrderCompletedEvent(this.id, this.orderNumber, LocalDateTime.now()));
        }
    }

    private void recalculateTotals() {
        double total = this.items.stream()
                .mapToDouble(item -> item.getLineTotal().amount())
                .sum();
        this.totalAmount = Money.of(total, this.totalAmount.currency());
    }

    public Long getId() {
        return id;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public LocalDateTime getOrderedAt() {
        return orderedAt;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public DeliveryInformation getDeliveryInformation() {
        return deliveryInformation;
    }

    public Money getTotalAmount() {
        return totalAmount;
    }

    public String getNotes() {
        return notes;
    }

    public List<SalesOrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public String getCurrency() {
        return totalAmount.currency();
    }
}
