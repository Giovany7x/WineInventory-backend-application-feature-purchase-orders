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

/**
 * Agregado raíz que representa una orden de venta completa dentro del dominio.
 * Contiene la información del comprador, los ítems solicitados y los datos de entrega,
 * además de la lógica necesaria para mantener la coherencia de la orden.
 */
@Entity
@Table(name = "sales_orders")
public class SalesOrder extends AuditableAbstractAggregateRoot<SalesOrder> {

    /** Identificador interno de la orden generado por la base de datos. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Identificador del comprador que originó la orden. */
    @Column(nullable = false)
    private Long buyerId;

    /** Código legible de la orden que se expone públicamente. */
    @Column(nullable = false, unique = true)
    private String orderNumber;

    /** Fecha y hora en la que se generó la orden. */
    @Column(nullable = false)
    private LocalDateTime orderedAt;

    /** Estado del flujo de la orden (pendiente, procesando, completada, etc.). */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    /** Información de entrega asociada a la orden. */
    @Embedded
    private DeliveryInformation deliveryInformation;

    /** Monto total de la orden expresado en la moneda seleccionada. */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "total_amount", nullable = false)),
            @AttributeOverride(name = "currency", column = @Column(name = "total_currency", nullable = false))
    })
    private Money totalAmount;

    /** Notas adicionales opcionales que el comprador puede adjuntar. */
    @Column(columnDefinition = "TEXT")
    private String notes;

    /** Colección de ítems que pertenecen a la orden. */
    @OneToMany(mappedBy = "salesOrder", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<SalesOrderItem> items = new ArrayList<>();

    protected SalesOrder() {
        // Required by JPA
    }

    /**
     * Constructor privado que garantiza la creación de una orden en un estado válido.
     * Se utiliza a través del método fábrica {@link #create(Long, String, DeliveryInformation, String)}.
     */
    private SalesOrder(Long buyerId, String currency, DeliveryInformation deliveryInformation, String notes) {
        this.buyerId = Objects.requireNonNull(buyerId, "The buyer identifier is required");
        this.deliveryInformation = Objects.requireNonNull(deliveryInformation, "Delivery information is required");
        this.notes = notes;
        this.status = OrderStatus.PENDING;
        this.orderNumber = generateOrderNumber();
        this.orderedAt = LocalDateTime.now();
        this.totalAmount = Money.of(0.0, Objects.requireNonNull(currency, "Currency is required"));
    }

    /**
     * Método fábrica que encapsula la creación de la orden para asegurar que siempre
     * se inicialice con los valores requeridos y reglas de negocio predefinidas.
     */
    public static SalesOrder create(Long buyerId, String currency, DeliveryInformation deliveryInformation, String notes) {
        return new SalesOrder(buyerId, currency, deliveryInformation, notes);
    }

    /**
     * Genera un identificador legible único utilizando un prefijo y un fragmento aleatorio.
     */
    private String generateOrderNumber() {
        return "SO-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(Locale.ROOT);
    }

    /**
     * Agrega un nuevo ítem a la orden verificando que utilice la misma moneda y recalcula el total.
     */
    public void addItem(SalesOrderItem item) {
        Objects.requireNonNull(item, "The item to add cannot be null");
        if (!item.getUnitPrice().currency().equalsIgnoreCase(this.totalAmount.currency())) {
            throw new IllegalArgumentException("All order items must share the same currency");
        }
        item.assignOrder(this);
        this.items.add(item);
        recalculateTotals();
    }

    /**
     * Cambia el estado de la orden y emite un evento de dominio cuando se completa.
     */
    public void updateStatus(OrderStatus newStatus) {
        Objects.requireNonNull(newStatus, "Order status is required");
        this.status = newStatus;
        if (OrderStatus.COMPLETED.equals(newStatus)) {
            this.addDomainEvent(new OrderCompletedEvent(this.id, this.orderNumber, LocalDateTime.now()));
        }
    }

    /**
     * Calcula nuevamente el total de la orden sumando el subtotal de cada ítem.
     */
    private void recalculateTotals() {
        double total = this.items.stream()
                .mapToDouble(item -> item.getLineTotal().amount())
                .sum();
        this.totalAmount = Money.of(total, this.totalAmount.currency());
    }

    /**
     * Accesores que exponen información de solo lectura para otras capas de la aplicación.
     */
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
