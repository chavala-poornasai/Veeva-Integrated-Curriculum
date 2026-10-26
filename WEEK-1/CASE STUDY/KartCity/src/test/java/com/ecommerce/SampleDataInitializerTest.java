package com.ecommerce;

import com.ecommerce.repository.CustomerRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.CustomerService;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.SampleDataInitializer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SampleDataInitializerTest {
    @Test
    void seedsProductsOnceIntoTheSharedCatalogRepository() {
        ProductRepository products = new ProductRepository();
        CustomerService customers = new CustomerService(new CustomerRepository());
        ProductService productService = new ProductService(products);
        SampleDataInitializer initializer = new SampleDataInitializer(productService, customers);

        initializer.initialize();
        initializer.initialize();

        assertEquals(5, productService.getAllProducts().size());
        assertEquals(2, productService.searchByName("AC").size());
        assertEquals("LG AC", productService.getProductById("PROD2001").getName());
        assertEquals(2, customers.getAllCustomers().size());
        assertEquals("CUST1001", customers.getAllCustomers().stream()
                .filter(customer -> customer.getEmail().equals("aarav@example.com"))
                .findFirst().orElseThrow().getCustomerId());
    }
}
