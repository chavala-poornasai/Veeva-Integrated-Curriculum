package com.ecommerce.service;

import com.ecommerce.exception.ProductNotFoundException;
import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.util.IdGenerator;

import java.math.BigDecimal;
import java.util.List;

public class ProductService {
    private final ProductRepository productRepository;
    private final IdGenerator productIdGenerator;

    public ProductService(ProductRepository productRepository) {
        this(productRepository, new IdGenerator("PROD", 2001));
    }

    public ProductService(ProductRepository productRepository, IdGenerator productIdGenerator) {
        this.productRepository = productRepository;
        this.productIdGenerator = productIdGenerator;
    }

    public synchronized Product addProduct(String name, String company, String description, BigDecimal price, int stock) {
        String productId = productIdGenerator.nextId(productRepository.findAll().stream()
                .map(Product::getProductId)
                .toList());
        Product product = new Product(productId, name, company, description, price, stock);
        productRepository.add(product);
        return product;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(String productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));
    }

    public List<Product> searchByName(String name) {
        return productRepository.findByName(name);
    }

    public List<Product> searchByNameAndExactPrice(String name, BigDecimal price) {
        return productRepository.findByNameAndExactPrice(name, price);
    }

    public List<Product> searchByNameAndPriceRange(String name, BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.findByNameAndPriceRange(name, minPrice, maxPrice);
    }

    public List<Product> searchByNameAndCompany(String name, String company) {
        return productRepository.findByNameAndCompany(name, company);
    }

    public List<Product> searchByNameCompanyAndPrice(String name, String company, BigDecimal price) {
        return productRepository.findByNameCompanyAndPrice(name, company, price);
    }

    public Product updateProduct(String productId, String name, String company, String description, BigDecimal price, Integer stock) {
        Product product = getProductById(productId);
        if (name != null && !name.isBlank()) {
            product.setName(name);
        }
        if (company != null && !company.isBlank()) {
            product.setCompany(company);
        }
        if (description != null) {
            product.setDescription(description);
        }
        if (price != null) {
            product.setPrice(price);
        }
        if (stock != null) {
            product.setStock(stock);
        }
        productRepository.save(product);
        return product;
    }

    public void deleteProduct(String productId) {
        if (!productRepository.deleteById(productId)) {
            throw new ProductNotFoundException("Product not found with id: " + productId);
        }
    }

    public void reduceStock(String productId, int quantity) {
        Product product = getProductById(productId);
        product.reduceStock(quantity);
        productRepository.save(product);
    }

    public void addStock(String productId, int quantity) {
        Product product = getProductById(productId);
        product.addStock(quantity);
        productRepository.save(product);
    }

    public synchronized void reduceStockForCheckout(java.util.Map<String, Integer> quantities) {
        for (var entry : quantities.entrySet()) {
            Product product = getProductById(entry.getKey());
            if (entry.getValue() == null || entry.getValue() <= 0 || product.getStock() < entry.getValue()) {
                throw new com.ecommerce.exception.InsufficientStockException(
                        "Not enough stock for product: " + product.getName());
            }
        }
        for (var entry : quantities.entrySet()) {
            getProductById(entry.getKey()).reduceStock(entry.getValue());
        }
    }
}
