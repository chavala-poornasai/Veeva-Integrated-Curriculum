package com.ecommerce.model;

import com.ecommerce.exception.InsufficientStockException;
import com.ecommerce.util.IdGenerator;

import java.math.BigDecimal;

public class Product {
    private final String productId;
    private String name;
    private String company;
    private String description;
    private BigDecimal price;
    private int stock;

    public Product(String productId, String name, String company, String description, BigDecimal price, int stock) {
        IdGenerator.validateId(productId, "PROD", "Product");
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name is required");
        }
        if (company == null || company.isBlank()) {
            throw new IllegalArgumentException("Company name is required");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        if (stock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }
        this.productId = productId;
        this.name = name.trim();
        this.company = company.trim();
        this.description = description == null ? "" : description.trim();
        this.price = price;
        this.stock = stock;
    }

    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name cannot be blank");
        }
        this.name = name.trim();
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        if (company == null || company.isBlank()) {
            throw new IllegalArgumentException("Company name cannot be blank");
        }
        this.company = company.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? "" : description.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }
        this.stock = stock;
    }

    public void addStock(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        stock += quantity;
    }

    public void reduceStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (quantity > stock) {
            throw new InsufficientStockException("Not enough stock for product: " + name);
        }
        stock -= quantity;
    }
}
