package com.ecommerce.service;

import com.ecommerce.exception.InsufficientStockException;
import com.ecommerce.model.Cart;
import com.ecommerce.model.CartItem;
import com.ecommerce.model.Product;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CartService {
    private final Map<String, Cart> cartsByCustomer = new HashMap<>();

    public Cart getCart(String customerId) {
        if (customerId == null || customerId.isBlank()) {
            throw new IllegalArgumentException("Customer ID is required");
        }
        return cartsByCustomer.computeIfAbsent(customerId, Cart::new);
    }

    public Cart addToCart(String customerId, Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (product.getStock() < quantity) {
            throw new InsufficientStockException("Not enough stock for product: " + product.getName());
        }

        Cart cart = getCart(customerId);
        CartItem existing = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(product.getProductId()))
                .findFirst()
                .orElse(null);

        if (existing != null) {
            int totalQuantity = existing.getQuantity() + quantity;
            if (product.getStock() < totalQuantity) {
                throw new InsufficientStockException("Requested quantity exceeds available stock");
            }
        }
        cart.addItem(new CartItem(product.getProductId(), product.getName(), product.getPrice(), quantity));
        return cart;
    }

    public Cart updateQuantity(String customerId, String productId, int quantity) {
        Cart cart = getCart(customerId);
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        cart.updateQuantity(productId, quantity);
        return cart;
    }

    public Cart removeProduct(String customerId, String productId) {
        Cart cart = getCart(customerId);
        cart.removeItem(productId);
        return cart;
    }

    public void clearCart(String customerId) {
        Cart cart = getCart(customerId);
        cart.clear();
    }

    public BigDecimal getTotal(String customerId) {
        return getCart(customerId).calculateSubtotal();
    }
}
