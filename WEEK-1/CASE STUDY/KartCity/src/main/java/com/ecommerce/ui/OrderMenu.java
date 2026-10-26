package com.ecommerce.ui;

import com.ecommerce.enums.OrderStatus;
import com.ecommerce.exception.InsufficientStockException;
import com.ecommerce.exception.InvalidOrderStateException;
import com.ecommerce.exception.PaymentFailedException;
import com.ecommerce.exception.ProductNotFoundException;
import com.ecommerce.model.Order;
import com.ecommerce.model.TrackingInfo;
import com.ecommerce.payment.CashOnDelivery;
import com.ecommerce.payment.CreditCardPayment;
import com.ecommerce.payment.DebitCardPayment;
import com.ecommerce.payment.PaymentMethod;
import com.ecommerce.payment.UpiPayment;
import com.ecommerce.service.CartService;
import com.ecommerce.service.OrderService;

import java.util.List;
import java.util.Scanner;

public class OrderMenu {
    private final OrderService orderService;
    private final CartService cartService;
    private final SessionContext session;

    public OrderMenu(OrderService orderService, CartService cartService, SessionContext session) {
        this.orderService = orderService;
        this.cartService = cartService;
        this.session = session;
    }

    public void showMenu(Scanner scanner) {
        while (true) {
            if (!session.isCustomerAuthenticated()) {
                System.out.println("Log in from Customer Menu before managing orders.");
                return;
            }
            System.out.println("\nOrder Menu");
            System.out.println("1. Place an order");
            System.out.println("2. View my orders");
            System.out.println("3. View order details");
            System.out.println("4. Track an order");
            System.out.println("5. Cancel a placed order");
            System.out.println("6. Request a return");
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
                    case "1" -> checkout(scanner, customerId);
                    case "2" -> showOrders(customerId);
                    case "3" -> showDetails(scanner, customerId);
                    case "4" -> trackOrder(scanner, customerId);
                    case "5" -> {
                        System.out.print("Order ID: ");
                        Order order = orderService.cancelOrder(customerId, scanner.nextLine().trim());
                        System.out.println("Order cancelled: " + order.getOrderId());
                    }
                    case "6" -> {
                        System.out.print("Order ID: ");
                        Order order = orderService.requestReturn(customerId, scanner.nextLine().trim());
                        System.out.println("Return requested for order " + order.getOrderId());
                    }
                    case "0" -> {
                        return;
                    }
                    default -> throw new IllegalStateException("Validated menu option was not handled: " + choice);
                }
            } catch (ProductNotFoundException | InsufficientStockException | PaymentFailedException
                     | InvalidOrderStateException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void checkout(Scanner scanner, String customerId) {
        if (cartService.getCart(customerId).isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }
        PaymentMethod paymentMethod = choosePayment(scanner);
        System.out.print("Coupon code (blank for none): ");
        String coupon = scanner.nextLine().trim();
        System.out.print("Gift card code (blank for none): ");
        String giftCard = scanner.nextLine().trim();
        Order order = orderService.checkout(customerId, paymentMethod,
                coupon.isBlank() ? null : coupon, giftCard.isBlank() ? null : giftCard);
        System.out.println("Order placed: " + order.getOrderId());
        System.out.println("Subtotal: INR " + order.getSubtotal()
                + " | Shipping: INR " + order.getShippingCost()
                + " | Discount: INR " + order.getDiscountAmount()
                + " | Total: INR " + order.getTotalAmount());
        if (order.getGiftCardAmount().compareTo(java.math.BigDecimal.ZERO) > 0) {
            System.out.println("Gift card applied: INR " + order.getGiftCardAmount());
        }
        System.out.println("Payment status: " + order.getPayment().getStatus());
    }

    private PaymentMethod choosePayment(Scanner scanner) {
        System.out.println("Payment method: 1. UPI  2. Debit card  3. Credit card  4. Cash on delivery");
        System.out.print("Choose: ");
        String choice = scanner.nextLine().trim();
        if (choice.equals("4")) {
            return new CashOnDelivery();
        }
        System.out.print("Simulate online payment success? (y/n): ");
        boolean successful = scanner.nextLine().trim().equalsIgnoreCase("y");
        return switch (choice) {
            case "1" -> new UpiPayment("simulated", successful);
            case "2" -> new DebitCardPayment("simulated", successful);
            case "3" -> new CreditCardPayment("simulated", successful);
            default -> throw new IllegalArgumentException("Invalid payment method");
        };
    }

    private void showOrders(String customerId) {
        List<Order> orders = orderService.getOrdersByCustomer(customerId);
        if (orders.isEmpty()) {
            System.out.println("You have no orders.");
            return;
        }
        orders.forEach(order -> System.out.printf("%s | %s | INR %s | %s%n",
                order.getOrderId(), order.getStatus(), order.getTotalAmount(), order.getCreatedAt()));
    }

    private void showDetails(Scanner scanner, String customerId) {
        System.out.print("Order ID: ");
        Order order = getOwnOrder(customerId, scanner.nextLine().trim());
        System.out.printf("Order %s | %s | Subtotal INR %s | Shipping INR %s | Discount INR %s | Total INR %s%n",
                order.getOrderId(), order.getStatus(), order.getSubtotal(), order.getShippingCost(),
                order.getDiscountAmount(), order.getTotalAmount());
        System.out.println("Gift card applied: INR " + order.getGiftCardAmount()
                + " | Payment due: INR " + order.getPayment().getAmount());
        System.out.println("Shipping to: " + order.getShippingAddress());
        order.getItems().forEach(item -> System.out.printf("%s | %s | Qty: %d | Unit: INR %s%n",
                item.getProductId(), item.getProductName(), item.getQuantity(), item.getUnitPrice()));
    }

    private void trackOrder(Scanner scanner, String customerId) {
        System.out.print("Order ID: ");
        Order order = getOwnOrder(customerId, scanner.nextLine().trim());
        TrackingInfo trackingInfo = order.getTrackingInfo();
        if (trackingInfo == null) {
            System.out.println("Tracking is not available until the order is dispatched.");
            return;
        }
        System.out.println(trackingInfo.getCourierName() + " | " + trackingInfo.getTrackingNumber());
        trackingInfo.getEvents().forEach(event -> System.out.printf("%s | %s | %s%n",
                event.timestamp(), event.location(), event.description()));
    }

    private Order getOwnOrder(String customerId, String orderId) {
        Order order = orderService.getOrderById(orderId);
        if (!order.getCustomerId().equals(customerId)) {
            throw new IllegalArgumentException("You can only access your own order.");
        }
        return order;
    }
}
