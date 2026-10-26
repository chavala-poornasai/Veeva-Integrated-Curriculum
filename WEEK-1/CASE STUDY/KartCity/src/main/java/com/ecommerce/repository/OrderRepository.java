package com.ecommerce.repository;

import com.ecommerce.model.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OrderRepository {
    private final Map<String, Order> ordersById = new HashMap<>();

    public void save(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        Order idOwner = ordersById.get(order.getOrderId());
        if (idOwner != null && idOwner != order) {
            throw new IllegalArgumentException("Order ID already exists: " + order.getOrderId());
        }
        ordersById.put(order.getOrderId(), order);
    }

    public void add(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        if (ordersById.containsKey(order.getOrderId())) {
            throw new IllegalArgumentException("Order ID already exists: " + order.getOrderId());
        }
        ordersById.put(order.getOrderId(), order);
    }
    public Optional<Order> findById(String orderId) {
        return Optional.ofNullable(ordersById.get(orderId));
    }

    public List<Order> findAll() {
        return new ArrayList<>(ordersById.values());
    }

    public List<Order> findByCustomerId(String customerId) {
        if (customerId == null || customerId.isBlank()) {
            return new ArrayList<>();
        }
        return ordersById.values().stream()
                .filter(order -> order.getCustomerId().equals(customerId))
                .toList();
    }
}
