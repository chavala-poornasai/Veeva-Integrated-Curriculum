package com.ecommerce.service;

import com.ecommerce.enums.OrderStatus;
import com.ecommerce.model.Order;
import com.ecommerce.model.TrackingInfo;

import java.util.UUID;

public class TrackingService {
    public TrackingInfo dispatchOrder(Order order) {
        return assignTracking(order, "KartCity Courier",
                "KKT" + UUID.randomUUID().toString().substring(0, 10).toUpperCase());
    }

    public TrackingInfo assignTracking(Order order, String courierName, String trackingNumber) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        if (order.getStatus() != OrderStatus.PLACED) {
            throw new IllegalArgumentException("Tracking can only be assigned to a placed order");
        }
        TrackingInfo trackingInfo = new TrackingInfo(courierName, trackingNumber);
        order.setTrackingInfo(trackingInfo);
        order.updateStatus(OrderStatus.DISPATCHED);
        return trackingInfo;
    }

    public void updateOrderStatus(Order order, OrderStatus nextStatus) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        order.updateStatus(nextStatus);
        if (order.getTrackingInfo() != null) {
            order.getTrackingInfo().addEvent("In transit", "Status updated to " + nextStatus.name());
        }
    }

    public void addTrackingEvent(Order order, String description) {
        if (order.getTrackingInfo() != null) {
            order.getTrackingInfo().addEvent("In transit", description);
        }
    }
}
