package com.ecommerce.ui;

import com.ecommerce.enums.OrderStatus;
import com.ecommerce.enums.SubscriptionPlan;
import com.ecommerce.exception.InvalidOrderStateException;
import com.ecommerce.exception.ProductNotFoundException;
import com.ecommerce.model.Address;
import com.ecommerce.model.Customer;
import com.ecommerce.model.Order;
import com.ecommerce.model.Product;
import com.ecommerce.service.CustomerService;
import com.ecommerce.service.DiscountService;
import com.ecommerce.service.GiftCardService;
import com.ecommerce.service.OrderService;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.SubscriptionService;

import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    private static final String DEMO_ADMIN_PASSWORD = "admin1234";
    private final ProductService productService;
    private final CustomerService customerService;
    private final OrderService orderService;
    private final DiscountService discountService;
    private final GiftCardService giftCardService;
    private final SubscriptionService subscriptionService;
    private final SessionContext session;

    public AdminMenu(ProductService productService, CustomerService customerService, OrderService orderService,
                     DiscountService discountService, GiftCardService giftCardService,
                     SubscriptionService subscriptionService, SessionContext session) {
        this.productService = productService;
        this.customerService = customerService;
        this.orderService = orderService;
        this.discountService = discountService;
        this.giftCardService = giftCardService;
        this.subscriptionService = subscriptionService;
        this.session = session;
    }

    public void showMenu(Scanner scanner) {
        if (!session.isAdminAuthenticated() && !authenticateAdmin(scanner)) {
            return;
        }
        while (true) {
            System.out.println("\nAdmin Menu");
            System.out.println("1. Add product");
            System.out.println("2. View all products");
            System.out.println("3. Search products");
            System.out.println("4. Update product");
            System.out.println("5. Delete product");
            System.out.println("6. View customers");
            System.out.println("7. View all orders");
            System.out.println("8. Update order status");
            System.out.println("9. Assign courier/tracking");
            System.out.println("10. Create gift card");
            System.out.println("11. Create coupon");
            System.out.println("12. View subscriptions");
            System.out.println("13. Log out admin");
            System.out.println("0. Return");
            try {
                String choice = ConsoleInput.readMenuChoice(scanner, "Choose an option: ",
                        "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                        "10", "11", "12", "13");
                if (choice == null) {
                    return;
                }
                if (choice.isEmpty()) {
                    continue;
                }
                switch (choice) {
                    case "1" -> addProduct(scanner);
                    case "2" -> printProducts(productService.getAllProducts());
                    case "3" -> searchProducts(scanner);
                    case "4" -> updateProduct(scanner);
                    case "5" -> deleteProduct(scanner);
                    case "6" -> viewCustomers(scanner);
                    case "7" -> viewOrders();
                    case "8" -> updateOrderStatus(scanner);
                    case "9" -> assignTracking(scanner);
                    case "10" -> createGiftCard(scanner);
                    case "11" -> createCoupon(scanner);
                    case "12" -> viewSubscriptions();
                    case "13" -> {
                        session.logoutAdmin();
                        System.out.println("Admin logged out.");
                        return;
                    }
                    case "0" -> {
                        return;
                    }
                    default -> throw new IllegalStateException("Validated menu option was not handled: " + choice);
                }
            } catch (ProductNotFoundException | InvalidOrderStateException | DateTimeException
                     | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private boolean authenticateAdmin(Scanner scanner) {
        System.out.print("Demo admin password: ");
        if (!DEMO_ADMIN_PASSWORD.equals(scanner.nextLine())) {
            System.out.println("Admin authentication failed.");
            return false;
        }
        session.loginAdmin();
        System.out.println("Admin authenticated. This demo password is not production-grade security.");
        return true;
    }

    private void addProduct(Scanner scanner) {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Company: ");
        String company = scanner.nextLine();
        System.out.print("Description: ");
        String description = scanner.nextLine();
        System.out.print("Price: ");
        BigDecimal price = new BigDecimal(scanner.nextLine().trim());
        System.out.print("Stock: ");
        int stock = Integer.parseInt(scanner.nextLine().trim());
        Product product = productService.addProduct(name, company, description, price, stock);
        System.out.println("Product added with ID " + product.getProductId());
    }

    private void searchProducts(Scanner scanner) {
        System.out.print("Product name: ");
        printProducts(productService.searchByName(scanner.nextLine()));
    }

    private void updateProduct(Scanner scanner) {
        System.out.print("Product ID: ");
        String id = scanner.nextLine().trim();
        Product current = productService.getProductById(id);
        System.out.print("Name [" + current.getName() + "]: ");
        String name = scanner.nextLine();
        System.out.print("Company [" + current.getCompany() + "]: ");
        String company = scanner.nextLine();
        System.out.print("Description [" + current.getDescription() + "]: ");
        String description = scanner.nextLine();
        System.out.print("Price [" + current.getPrice() + "]: ");
        String price = scanner.nextLine().trim();
        System.out.print("Stock [" + current.getStock() + "]: ");
        String stock = scanner.nextLine().trim();
        productService.updateProduct(id, name.isBlank() ? null : name, company.isBlank() ? null : company,
                description, price.isBlank() ? null : new BigDecimal(price),
                stock.isBlank() ? null : Integer.parseInt(stock));
        System.out.println("Product updated. Existing orders retain their purchase-time item details.");
    }

    private void deleteProduct(Scanner scanner) {
        System.out.print("Product ID: ");
        productService.deleteProduct(scanner.nextLine().trim());
        System.out.println("Product deleted from the active catalog.");
    }

    private void viewCustomers(Scanner scanner) {
        System.out.print("Search by name (blank lists all): ");
        String search = scanner.nextLine();
        List<Customer> customers = search.isBlank()
                ? customerService.getAllCustomers()
                : customerService.searchByName(search);
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }
        customers.forEach(customer -> System.out.printf("%s | %s | %s%n",
                customer.getCustomerId(), customer.getName(), customer.getEmail()));
    }

    private void viewOrders() {
        List<Order> orders = orderService.getAllOrders();
        if (orders.isEmpty()) {
            System.out.println("No orders found.");
            return;
        }
        orders.forEach(order -> System.out.printf("%s | Customer: %s | %s | INR %s%n",
                order.getOrderId(), order.getCustomerId(), order.getStatus(), order.getTotalAmount()));
    }

    private void updateOrderStatus(Scanner scanner) {
        System.out.print("Order ID: ");
        String id = scanner.nextLine().trim();
        System.out.println("Allowed statuses: " + List.of(OrderStatus.values()));
        System.out.print("Next status: ");
        OrderStatus nextStatus = OrderStatus.valueOf(scanner.nextLine().trim().toUpperCase());
        orderService.updateOrderStatus(id, nextStatus);
        System.out.println("Order status updated.");
    }

    private void assignTracking(Scanner scanner) {
        System.out.print("Order ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Courier name: ");
        String courier = scanner.nextLine();
        System.out.print("Tracking number: ");
        String trackingNumber = scanner.nextLine();
        orderService.assignTracking(id, courier, trackingNumber);
        System.out.println("Tracking assigned and order dispatched.");
    }

    private void createGiftCard(Scanner scanner) {
        System.out.print("Unique code: ");
        String code = scanner.nextLine().trim();
        System.out.print("Balance: ");
        BigDecimal balance = new BigDecimal(scanner.nextLine().trim());
        System.out.print("Expiry date (YYYY-MM-DD): ");
        LocalDate expiry = LocalDate.parse(scanner.nextLine().trim());
        giftCardService.createGiftCard(code, balance, expiry);
        System.out.println("Gift card created.");
    }

    private void createCoupon(Scanner scanner) {
        System.out.print("Unique coupon code: ");
        String code = scanner.nextLine().trim();
        System.out.print("Discount type (1 = percentage, 2 = fixed amount): ");
        String type = scanner.nextLine().trim();
        System.out.print(type.equals("1") ? "Percentage discount (0-100): " : "Fixed discount amount: ");
        BigDecimal discount = new BigDecimal(scanner.nextLine().trim());
        System.out.print("Minimum delivered orders: ");
        long minOrders = Long.parseLong(scanner.nextLine().trim());
        System.out.print("Minimum eligible spend: ");
        BigDecimal minSpend = new BigDecimal(scanner.nextLine().trim());
        System.out.print("Expiry date (YYYY-MM-DD): ");
        LocalDate expiry = LocalDate.parse(scanner.nextLine().trim());
        System.out.print("Redemption limit (0 = unlimited): ");
        int limit = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Maximum discount (blank for no cap): ");
        String maximumText = scanner.nextLine().trim();
        BigDecimal maximum = maximumText.isBlank() ? null : new BigDecimal(maximumText);
        if (type.equals("1")) {
            discountService.createPercentageCoupon(code, "Admin-created coupon", discount, minOrders,
                    minSpend, expiry, limit, maximum);
        } else if (type.equals("2")) {
            discountService.createFixedCoupon(code, "Admin-created coupon", discount, minOrders,
                    minSpend, expiry, limit, maximum);
        } else {
            throw new IllegalArgumentException("Invalid discount type");
        }
        System.out.println("Coupon created.");
    }

    private void viewSubscriptions() {
        var subscriptions = subscriptionService.getAllSubscriptions();
        if (subscriptions.isEmpty()) {
            System.out.println("No subscriptions found.");
            return;
        }
        subscriptions.forEach(subscription -> System.out.printf("%s | Customer: %s | %s | Expires: %s%n",
                subscription.getSubscriptionId(), subscription.getCustomerId(), subscription.getPlan(),
                subscription.getExpiryDate()));
    }

    private void printProducts(List<Product> products) {
        if (products.isEmpty()) {
            System.out.println("No products found.");
            return;
        }
        products.forEach(product -> System.out.printf("%s | %s | %s | INR %s | Stock: %d%n",
                product.getProductId(), product.getName(), product.getCompany(), product.getPrice(), product.getStock()));
    }
}
