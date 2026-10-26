package com.ecommerce;

import com.ecommerce.exception.InsufficientStockException;
import com.ecommerce.model.Product;
import com.ecommerce.service.CartService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CartServiceTest {
    @Test
    void repeatedProductsMergeAndCartItemsAreDefensiveCopies() {
        CartService carts = new CartService();
        Product product = new Product("PROD2001", "Mouse", "Example", "Wireless mouse",
                new BigDecimal("100"), 5);

        carts.addToCart("CUST1001", product, 3);
        carts.addToCart("CUST1001", product, 2);
        assertEquals(5, carts.getCart("CUST1001").getItems().get(0).getQuantity());
        assertThrows(InsufficientStockException.class,
                () -> carts.addToCart("CUST1001", product, 1));

        carts.getCart("CUST1001").getItems().get(0).setQuantity(1);
        assertEquals(new BigDecimal("500"), carts.getTotal("CUST1001"));
    }
}
