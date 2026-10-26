package com.ecommerce.model;

import com.ecommerce.enums.OrderStatus;
import com.ecommerce.exception.InvalidOrderStateException;
import com.ecommerce.util.IdGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private final String orderId;
    private final String customerId;
    private final List<OrderItem> items = new ArrayList<>();
    private OrderStatus status;
    private BigDecimal subtotal;
    private BigDecimal shippingCost;
    private BigDecimal discountAmount;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private String shippingAddress;
    private TrackingInfo trackingInfo;
    private Payment payment;
    private BigDecimal giftCardAmount;
    private boolean inventoryRestocked;

    public Order(String orderId, String customerId, List<OrderItem> items, BigDecimal subtotal,
                 BigDecimal shippingCost, BigDecimal discountAmount, BigDecimal totalAmount,
                 String shippingAddress) {
        this(orderId, customerId, items, subtotal, shippingCost, discountAmount, totalAmount, shippingAddress, null);
    }

    public Order(String orderId, String customerId, List<OrderItem> items, BigDecimal subtotal,
                 BigDecimal shippingCost, BigDecimal discountAmount, BigDecimal totalAmount,
                 String shippingAddress, Payment payment) {
        this(orderId, customerId, items, subtotal, shippingCost, discountAmount, totalAmount,
                shippingAddress, payment, BigDecimal.ZERO);
    }

    public Order(String orderId, String customerId, List<OrderItem> items, BigDecimal subtotal,
                 BigDecimal shippingCost, BigDecimal discountAmount, BigDecimal totalAmount,
                 String shippingAddress, Payment payment, BigDecimal giftCardAmount) {
        IdGenerator.validateId(orderId, "ORD", "Order");
        IdGenerator.validateId(customerId, "CUST", "Customer");
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item");
        }
        this.orderId = orderId;
        this.customerId = customerId;
        this.items.addAll(items);
        this.subtotal = subtotal;
        this.shippingCost = shippingCost;
        this.discountAmount = discountAmount;
        this.totalAmount = totalAmount;
        this.createdAt = LocalDateTime.now();
        this.status = OrderStatus.PLACED;
        this.shippingAddress = shippingAddress == null ? "" : shippingAddress;
        this.payment = payment;
        this.giftCardAmount = giftCardAmount == null ? BigDecimal.ZERO : giftCardAmount;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public List<OrderItem> getItems() {
        return new ArrayList<>(items);
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void updateStatus(OrderStatus nextStatus) {
        if (nextStatus == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        if (!status.canTransitionTo(nextStatus)) {
            throw new InvalidOrderStateException("Invalid state transition from " + status + " to " + nextStatus);
        }
        this.status = nextStatus;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public BigDecimal getShippingCost() {
        return shippingCost;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public TrackingInfo getTrackingInfo() {
        return trackingInfo;
    }

    public void setTrackingInfo(TrackingInfo trackingInfo) {
        this.trackingInfo = trackingInfo;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public BigDecimal getGiftCardAmount() {
        return giftCardAmount;
    }

    public boolean markInventoryRestocked() {
        if (inventoryRestocked) {
            return false;
        }
        inventoryRestocked = true;
        return true;
    }
}
