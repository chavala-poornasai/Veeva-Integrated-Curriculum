package com.ecommerce.model;

import com.ecommerce.util.IdGenerator;

import java.math.BigDecimal;

public class CartItem {
    private final String productId;
    private final String productName;
    private int quantity;
    private final BigDecimal unitPrice;

    public CartItem(String productId, String productName, BigDecimal unitPrice, int quantity) {
        IdGenerator.validateId(productId, "PROD", "Product");
        if (productName == null || productName.isBlank()) {
            throw new IllegalArgumentException("Product name is required");
        }
        if (unitPrice == null || unitPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Unit price must be positive");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.productId = productId;
        this.productName = productName.trim();
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.quantity = quantity;
    }

    public void increaseQuantity(int increment) {
        if (increment <= 0) {
            throw new IllegalArgumentException("Increment must be positive");
        }
        this.quantity += increment;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public BigDecimal getLineTotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
