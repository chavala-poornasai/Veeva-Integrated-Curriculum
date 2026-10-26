package com.ecommerce.service;

import com.ecommerce.model.Address;

import java.math.BigDecimal;
public class SampleDataInitializer {
    private final ProductService productService;
    private final CustomerService customerService;

    public SampleDataInitializer(ProductService productService, CustomerService customerService) {
        this.productService = productService;
        this.customerService = customerService;
    }

    public void initialize() {
        addProductIfMissing("LG AC", "LG", "1.5 ton inverter air conditioner", "35000.00", 10);
        addProductIfMissing("Samsung AC", "Samsung", "Energy-efficient split air conditioner", "40000.00", 8);
        addProductIfMissing("Dell Laptop", "Dell", "Core i5 laptop", "55000.00", 5);
        addProductIfMissing("HP Laptop", "HP", "Business laptop", "48000.00", 7);
        addProductIfMissing("Samsung Mobile", "Samsung", "Android smartphone", "25000.00", 12);

        if (customerService.findByEmail("aarav@example.com").isEmpty()) {
            customerService.registerCustomer("Aarav", "aarav@example.com", "pass1234", "9876543210",
                    new Address("12 Gandhi Road", "Hyderabad", "Telangana", "500001"));
        }
        if (customerService.findByEmail("meera@example.com").isEmpty()) {
            customerService.registerCustomer("Meera", "meera@example.com", "pass1234", "9123456780",
                    new Address("45 MG Road", "Bangalore", "Karnataka", "560001"));
        }
    }

    private void addProductIfMissing(String name, String company, String description, String price, int stock) {
        boolean alreadyPresent = productService.searchByName(name).stream()
                .anyMatch(product -> product.getName().equalsIgnoreCase(name)
                        && product.getCompany().equalsIgnoreCase(company));
        if (!alreadyPresent) {
            productService.addProduct(name, company, description, new BigDecimal(price), stock);
        }
    }
}
