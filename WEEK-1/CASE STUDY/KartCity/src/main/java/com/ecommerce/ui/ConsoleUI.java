package com.ecommerce.ui;

import com.ecommerce.service.CustomerService;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.OrderService;
import com.ecommerce.service.CartService;
import com.ecommerce.service.DiscountService;
import com.ecommerce.service.GiftCardService;
import com.ecommerce.service.SubscriptionService;

import java.util.Scanner;

public class ConsoleUI {
    private final CustomerMenu customerMenu;
    private final ProductMenu productMenu;
    private final CartMenu cartMenu;
    private final OrderMenu orderMenu;
    private final AdminMenu adminMenu;
    private final SessionContext session = new SessionContext();

    public ConsoleUI(CustomerService customerService,
                    ProductService productService,
                    CartService cartService,
                    OrderService orderService,
                    DiscountService discountService,
                    GiftCardService giftCardService,
                    SubscriptionService subscriptionService) {
        this.customerMenu = new CustomerMenu(customerService, orderService, subscriptionService, session);
        this.productMenu = new ProductMenu(productService);
        this.cartMenu = new CartMenu(cartService, productService, session);
        this.orderMenu = new OrderMenu(orderService, cartService, session);
        this.adminMenu = new AdminMenu(productService, customerService, orderService, discountService,
                giftCardService, subscriptionService, session);
    }

    public void start(Scanner scanner) {
        while (true) {
            System.out.println("\nKartCity Store");
            System.out.println("1. Customer Menu");
            System.out.println("2. Product Menu");
            System.out.println("3. Cart Menu");
            System.out.println("4. Order Menu");
            System.out.println("5. Admin Menu");
            System.out.println("0. Exit");

            String choice = ConsoleInput.readMenuChoice(scanner, "Choose an option: ",
                    "0", "1", "2", "3", "4", "5");
            if (choice == null) {
                System.out.println("Goodbye!");
                return;
            }
            if (choice.isEmpty()) {
                continue;
            }
            switch (choice) {
                case "1" -> customerMenu.showMenu(scanner);
                case "2" -> productMenu.showMenu(scanner);
                case "3" -> cartMenu.showMenu(scanner);
                case "4" -> orderMenu.showMenu(scanner);
                case "5" -> adminMenu.showMenu(scanner);
                case "0" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> throw new IllegalStateException("Validated menu option was not handled: " + choice);
            }
        }
    }
}
