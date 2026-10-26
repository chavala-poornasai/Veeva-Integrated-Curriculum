package com.ecommerce.repository;

import com.ecommerce.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProductRepository {
    private final Map<String, Product> productsById = new HashMap<>();

    public void save(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        Product idOwner = productsById.get(product.getProductId());
        if (idOwner != null && idOwner != product) {
            throw new IllegalArgumentException("Product ID already exists: " + product.getProductId());
        }
        productsById.put(product.getProductId(), product);
    }

    public void add(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (productsById.containsKey(product.getProductId())) {
            throw new IllegalArgumentException("Product ID already exists: " + product.getProductId());
        }
        productsById.put(product.getProductId(), product);
    }

    public Optional<Product> findById(String productId) {
        return Optional.ofNullable(productsById.get(productId));
    }

    public List<Product> findAll() {
        return new ArrayList<>(productsById.values());
    }

    public List<Product> findByName(String name) {
        if (name == null || name.isBlank()) {
            return new ArrayList<>();
        }
        String normalized = name.trim().toLowerCase();
        return productsById.values().stream()
                .filter(product -> product.getName().toLowerCase().contains(normalized))
                .toList();
    }

    public List<Product> findByNameAndExactPrice(String name, BigDecimal price) {
        return findByName(name).stream()
                .filter(product -> product.getPrice().compareTo(price) == 0)
                .toList();
    }

    public List<Product> findByNameAndPriceRange(String name, BigDecimal minPrice, BigDecimal maxPrice) {
        if (minPrice == null || maxPrice == null || minPrice.compareTo(maxPrice) > 0) {
            throw new IllegalArgumentException("Price range is invalid");
        }
        return findByName(name).stream()
                .filter(product -> product.getPrice().compareTo(minPrice) >= 0 && product.getPrice().compareTo(maxPrice) <= 0)
                .toList();
    }

    public List<Product> findByNameAndCompany(String name, String company) {
        return findByName(name).stream()
                .filter(product -> product.getCompany().equalsIgnoreCase(company))
                .toList();
    }

    public List<Product> findByNameCompanyAndPrice(String name, String company, BigDecimal price) {
        return findByNameAndCompany(name, company).stream()
                .filter(product -> product.getPrice().compareTo(price) == 0)
                .toList();
    }

    public boolean deleteById(String productId) {
        return productsById.remove(productId) != null;
    }
}
