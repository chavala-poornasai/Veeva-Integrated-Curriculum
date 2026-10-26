package com.ecommerce;

import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.ProductService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {
    @Test
    void shouldFindProductsByNameAndRange() {
        ProductRepository repository = new ProductRepository();
        ProductService service = new ProductService(repository);

        Product dell = service.addProduct("Laptop", "Dell", "Gaming laptop", new BigDecimal("50000"), 10);
        Product hp = service.addProduct("Laptop", "HP", "Business laptop", new BigDecimal("70000"), 8);
        assertEquals("PROD2001", dell.getProductId());
        assertEquals("PROD2002", hp.getProductId());

        List<Product> result = service.searchByNameAndPriceRange("Laptop", new BigDecimal("45000"), new BigDecimal("65000"));

        assertEquals(1, result.size());
        assertEquals("Dell", result.get(0).getCompany());
    }

    @Test
    void generatedProductIdsSupportLookupUpdateAndDeletion() {
        ProductRepository repository = new ProductRepository();
        ProductService service = new ProductService(repository);
        Product first = service.addProduct("Tablet", "Brand", "Tablet", new BigDecimal("15000"), 4);
        Product second = service.addProduct("Phone", "Brand", "Phone", new BigDecimal("10000"), 2);

        assertNotEquals(first.getProductId(), second.getProductId());
        assertEquals(first, service.getProductById("PROD2001"));
        service.updateProduct(first.getProductId(), "Tablet Pro", null, null, null, null);
        assertEquals("Tablet Pro", service.getProductById(first.getProductId()).getName());
        service.deleteProduct(first.getProductId());
        assertThrows(com.ecommerce.exception.ProductNotFoundException.class,
                () -> service.getProductById(first.getProductId()));
        assertEquals("PROD2003", service.addProduct("Watch", "Brand", "Watch", new BigDecimal("5000"), 3).getProductId());

        ProductService restartedService = new ProductService(repository);
        assertEquals("PROD2004", restartedService.addProduct("Camera", "Brand", "Camera",
                new BigDecimal("20000"), 1).getProductId());
    }
}
