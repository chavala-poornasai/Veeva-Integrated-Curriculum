package com.ecommerce;

import com.ecommerce.payment.PaymentService;
import com.ecommerce.repository.CouponRepository;
import com.ecommerce.repository.CustomerRepository;
import com.ecommerce.repository.GiftCardRepository;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.SubscriptionRepository;
import com.ecommerce.service.CartService;
import com.ecommerce.service.CustomerService;
import com.ecommerce.service.DiscountService;
import com.ecommerce.service.GiftCardService;
import com.ecommerce.service.OrderService;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.SampleDataInitializer;
import com.ecommerce.service.SubscriptionService;
import com.ecommerce.service.TrackingService;
import com.ecommerce.util.IdGenerator;
import com.ecommerce.ui.ConsoleUI;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CustomerRepository customerRepository = new CustomerRepository();
        ProductRepository productRepository = new ProductRepository();
        OrderRepository orderRepository = new OrderRepository();
        SubscriptionRepository subscriptionRepository = new SubscriptionRepository();
        CouponRepository couponRepository = new CouponRepository();
        GiftCardRepository giftCardRepository = new GiftCardRepository();

        IdGenerator customerIds = new IdGenerator("CUST", 1001);
        IdGenerator productIds = new IdGenerator("PROD", 2001);
        IdGenerator orderIds = new IdGenerator("ORD", 3001);
        CustomerService customerService = new CustomerService(customerRepository, orderRepository, customerIds);
        ProductService productService = new ProductService(productRepository, productIds);
        CartService cartService = new CartService();
        DiscountService discountService = new DiscountService(couponRepository);
        GiftCardService giftCardService = new GiftCardService(giftCardRepository);
        TrackingService trackingService = new TrackingService();
        PaymentService paymentService = new PaymentService();
        SubscriptionService subscriptionService = new SubscriptionService(subscriptionRepository, customerService, paymentService);
        OrderService orderService = new OrderService(
                customerService,
                productService,
                cartService,
                orderRepository,
                trackingService,
                discountService,
                giftCardService,
                paymentService,
                subscriptionService,
                new BigDecimal("50.00"),
                orderIds);

        new SampleDataInitializer(productService, customerService).initialize();
        if (giftCardRepository.findByCode("KART100").isEmpty()) {
            giftCardService.createGiftCard("KART100", new BigDecimal("2000"),
                    LocalDate.now().plusMonths(6));
        }

        ConsoleUI consoleUI = new ConsoleUI(customerService, productService, cartService, orderService,
                discountService, giftCardService, subscriptionService);
        try (Scanner scanner = new Scanner(System.in)) {
            consoleUI.start(scanner);
        }
    }
}
