package com.ecommerce.model;

import com.ecommerce.util.IdGenerator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    private final String customerId;
    private final Map<String, CartItem> items = new HashMap<>();

    public Cart(String customerId) {
        IdGenerator.validateId(customerId, "CUST", "Customer");
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public List<CartItem> getItems() {
        return items.values().stream()
                .map(item -> new CartItem(item.getProductId(), item.getProductName(),
                        item.getUnitPrice(), item.getQuantity()))
                .toList();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void addItem(CartItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Cart item cannot be null");
        }
        CartItem existing = items.get(item.getProductId());
        if (existing == null) {
            items.put(item.getProductId(), item);
        } else {
            existing.increaseQuantity(item.getQuantity());
        }
    }

    public void updateQuantity(String productId, int quantity) {
        if (productId == null || productId.isBlank()) {
            throw new IllegalArgumentException("Product ID is required");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        CartItem item = items.get(productId);
        if (item == null) {
            throw new IllegalArgumentException("Product not found in cart");
        }
        item.setQuantity(quantity);
    }

    public void removeItem(String productId) {
        if (productId == null || productId.isBlank()) {
            throw new IllegalArgumentException("Product ID is required");
        }
        items.remove(productId);
    }

    public void clear() {
        items.clear();
    }

    public BigDecimal calculateSubtotal() {
        return items.values().stream()
                .map(CartItem::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
