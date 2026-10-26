package com.ecommerce.ui;

import com.ecommerce.model.Address;
import com.ecommerce.model.Customer;
import com.ecommerce.service.AuthenticationService;
import com.ecommerce.service.CustomerService;
import com.ecommerce.service.OrderService;
import com.ecommerce.service.SubscriptionService;
import com.ecommerce.enums.SubscriptionPlan;
import com.ecommerce.exception.PaymentFailedException;
import com.ecommerce.payment.CreditCardPayment;
import com.ecommerce.payment.DebitCardPayment;
import com.ecommerce.payment.PaymentMethod;
import com.ecommerce.payment.UpiPayment;

import java.util.Scanner;

public class CustomerMenu {
    private final CustomerService customerService;
    private final OrderService orderService;
    private final SessionContext session;
    private final SubscriptionService subscriptionService;
    private final AuthenticationService authenticationService = new AuthenticationService();

    public CustomerMenu(CustomerService customerService, OrderService orderService,
                        SubscriptionService subscriptionService, SessionContext session) {
        this.customerService = customerService;
        this.orderService = orderService;
        this.subscriptionService = subscriptionService;
        this.session = session;
    }

    public void showMenu(Scanner scanner) {
        while (true) {
            System.out.println("\nCustomer Menu" +
                    (session.isCustomerAuthenticated() ? " - " + session.getCurrentCustomer().getName() : ""));
            System.out.println("1. Register");
            System.out.println("2. Log in");
            System.out.println("3. View my profile");
            System.out.println("4. Update my details");
            System.out.println("5. Delete my account");
            System.out.println("6. Log out");
            System.out.println("7. Manage subscriptions");
            System.out.println("0. Return");
            String choice = ConsoleInput.readMenuChoice(scanner, "Choose an option: ",
                    "0", "1", "2", "3", "4", "5", "6", "7");
            if (choice == null) {
                return;
            }
            if (choice.isEmpty()) {
                continue;
            }
            switch (choice) {
                case "1" -> register(scanner);
                case "2" -> login(scanner);
                case "3" -> showProfile();
                case "4" -> update(scanner);
                case "5" -> deleteAccount(scanner);
                case "6" -> {
                    session.logoutCustomer();
                    System.out.println("Logged out.");
                }
                case "7" -> manageSubscriptions(scanner);
                case "0" -> {
                    return;
                }
                default -> throw new IllegalStateException("Validated menu option was not handled: " + choice);
            }
        }
    }

    private void register(Scanner scanner) {
        try {
            System.out.print("Full name: ");
            String name = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Password (at least 8 characters): ");
            String password = scanner.nextLine();
            System.out.print("Phone number: ");
            String phone = scanner.nextLine();
            Address address = readAddress(scanner);
            Customer customer = customerService.registerCustomer(name, email, password, phone, address);
            System.out.println("Account created. Your customer ID is " + customer.getCustomerId());
            System.out.println("Log in to access your cart and orders.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void login(Scanner scanner) {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        Customer customer = customerService.findByEmail(email).orElse(null);
        if (!authenticationService.authenticate(customer, password)) {
            System.out.println("Invalid email or password.");
            return;
        }
        session.login(customer);
        System.out.println("Welcome, " + customer.getName() + ".");
    }

    private void showProfile() {
        if (!requireLogin()) {
            return;
        }
        Customer customer = session.getCurrentCustomer();
        System.out.println("Customer ID: " + customer.getCustomerId());
        System.out.println("Name: " + customer.getName());
        System.out.println("Email: " + customer.getEmail());
        System.out.println("Phone: " + customer.getPhoneNumber());
        System.out.println("Address: " + customer.getAddress());
    }

    private void update(Scanner scanner) {
        if (!requireLogin()) {
            return;
        }
        try {
            Customer customer = session.getCurrentCustomer();
            System.out.print("New name (blank to keep current): ");
            String name = scanner.nextLine();
            System.out.print("New email (blank to keep current): ");
            String email = scanner.nextLine();
            System.out.print("New phone (blank to keep current): ");
            String phone = scanner.nextLine();
            System.out.print("Update address? (y/n): ");
            Address address = scanner.nextLine().trim().equalsIgnoreCase("y") ? readAddress(scanner) : null;
            customerService.updateCustomer(customer.getCustomerId(),
                    name.isBlank() ? null : name,
                    email.isBlank() ? null : email,
                    phone.isBlank() ? null : phone,
                    address);
            System.out.print("Change password? (y/n): ");
            if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
                System.out.print("New password (at least 8 characters): ");
                customerService.updatePassword(customer.getCustomerId(), scanner.nextLine());
            }
            System.out.println("Account updated.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void deleteAccount(Scanner scanner) {
        if (!requireLogin()) {
            return;
        }
        Customer customer = session.getCurrentCustomer();
        if (!orderService.getOrdersByCustomer(customer.getCustomerId()).isEmpty()) {
            System.out.println("Accounts with order history cannot be deleted.");
            return;
        }
        System.out.print("Delete this account? (y/n): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
            customerService.deleteCustomer(customer.getCustomerId());
            session.logoutCustomer();
            System.out.println("Account deleted.");
        }
    }

    private boolean requireLogin() {
        if (!session.isCustomerAuthenticated()) {
            System.out.println("Log in first to use this option.");
            return false;
        }
        return true;
    }

    private void manageSubscriptions(Scanner scanner) {
        if (!requireLogin()) {
            return;
        }
        for (SubscriptionPlan plan : SubscriptionPlan.values()) {
            System.out.printf("%s - %d months - INR %s%n", plan, plan.getMonths(), plan.getBasePrice());
        }
        System.out.print("Plan name to purchase (or blank to return): ");
        String choice = scanner.nextLine().trim();
        if (choice.isBlank()) {
            return;
        }
        try {
            SubscriptionPlan plan = SubscriptionPlan.valueOf(choice.toUpperCase());
            PaymentMethod method = chooseSubscriptionPayment(scanner);
            var subscription = subscriptionService.purchaseSubscription(
                    session.getCurrentCustomer().getCustomerId(), plan, method);
            System.out.println("Subscription active until " + subscription.getExpiryDate()
                    + ". Eligible orders receive free shipping.");
        } catch (IllegalArgumentException | PaymentFailedException e) {
            System.out.println(e.getMessage());
        }
    }

    private PaymentMethod chooseSubscriptionPayment(Scanner scanner) {
        System.out.println("Subscription payment: 1. UPI  2. Debit card  3. Credit card");
        System.out.print("Choose: ");
        String choice = scanner.nextLine().trim();
        System.out.print("Simulate payment success? (y/n): ");
        boolean successful = scanner.nextLine().trim().equalsIgnoreCase("y");
        return switch (choice) {
            case "1" -> new UpiPayment("simulated", successful);
            case "2" -> new DebitCardPayment("simulated", successful);
            case "3" -> new CreditCardPayment("simulated", successful);
            default -> throw new IllegalArgumentException("Invalid payment method");
        };
    }

    private Address readAddress(Scanner scanner) {
        System.out.print("Street: ");
        String street = scanner.nextLine();
        System.out.print("City: ");
        String city = scanner.nextLine();
        System.out.print("State: ");
        String state = scanner.nextLine();
        System.out.print("Postal code: ");
        String postalCode = scanner.nextLine();
        return new Address(street, city, state, postalCode);
    }
}
