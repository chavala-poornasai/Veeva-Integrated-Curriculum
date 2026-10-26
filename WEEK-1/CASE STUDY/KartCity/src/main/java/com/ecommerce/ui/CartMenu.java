package com.ecommerce.ui;

import com.ecommerce.exception.InsufficientStockException;
import com.ecommerce.exception.ProductNotFoundException;
import com.ecommerce.model.Cart;
import com.ecommerce.model.Product;
import com.ecommerce.service.CartService;
import com.ecommerce.service.ProductService;

import java.util.Scanner;

public class CartMenu {
    private final CartService cartService;
    private final ProductService productService;
    private final SessionContext session;

    public CartMenu(CartService cartService, ProductService productService, SessionContext session) {
        this.cartService = cartService;
        this.productService = productService;
        this.session = session;
    }

    public void showMenu(Scanner scanner) {
        while (true) {
            if (!session.isCustomerAuthenticated()) {
                System.out.println("Log in from Customer Menu before using your cart.");
                return;
            }
            System.out.println("\nCart Menu");
            System.out.println("1. Add a product");
            System.out.println("2. View cart");
            System.out.println("3. Update quantity");
            System.out.println("4. Remove a product");
            System.out.println("5. View subtotal");
            System.out.println("6. Clear cart");
            System.out.println("0. Return");
            String customerId = session.getCurrentCustomer().getCustomerId();

            try {
                String choice = ConsoleInput.readMenuChoice(scanner, "Choose an option: ",
                        "0", "1", "2", "3", "4", "5", "6");
                if (choice == null) {
                    return;
                }
                if (choice.isEmpty()) {
                    continue;
                }
                switch (choice) {
                    case "1" -> addProduct(scanner, customerId);
                    case "2" -> showCart(customerId);
                    case "3" -> updateQuantity(scanner, customerId);
                    case "4" -> {
                        System.out.print("Product ID: ");
                        cartService.removeProduct(customerId, scanner.nextLine().trim());
                        System.out.println("Product removed from cart.");
                    }
                    case "5" -> System.out.println("Subtotal: INR " + cartService.getTotal(customerId));
                    case "6" -> {
                        cartService.clearCart(customerId);
                        System.out.println("Cart cleared.");
                    }
                    case "0" -> {
                        return;
                    }
                    default -> throw new IllegalStateException("Validated menu option was not handled: " + choice);
                }
            } catch (ProductNotFoundException | InsufficientStockException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void addProduct(Scanner scanner, String customerId) {
        System.out.print("Product ID: ");
        Product product = productService.getProductById(scanner.nextLine().trim());
        System.out.print("Quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine().trim());
        Cart cart = cartService.addToCart(customerId, product, quantity);
        System.out.println("Added to cart. Subtotal: INR " + cart.calculateSubtotal());
    }

    private void updateQuantity(Scanner scanner, String customerId) {
        System.out.print("Product ID: ");
        String productId = scanner.nextLine().trim();
        System.out.print("New quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine().trim());
        Product product = productService.getProductById(productId);
        if (quantity > product.getStock()) {
            throw new InsufficientStockException("Requested quantity exceeds available stock");
        }
        cartService.updateQuantity(customerId, productId, quantity);
        System.out.println("Quantity updated.");
    }

    private void showCart(String customerId) {
        Cart cart = cartService.getCart(customerId);
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }
        cart.getItems().forEach(item -> System.out.printf("%s | %s | Qty: %d | Unit: INR %s | Total: INR %s%n",
                item.getProductId(), item.getProductName(), item.getQuantity(),
                item.getUnitPrice(), item.getLineTotal()));
        System.out.println("Subtotal: INR " + cart.calculateSubtotal());
    }
}
