package com.ecommerce;

import com.ecommerce.model.Address;
import com.ecommerce.model.Customer;
import com.ecommerce.model.Product;
import com.ecommerce.payment.CashOnDelivery;
import com.ecommerce.payment.UpiPayment;
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
import com.ecommerce.service.SubscriptionService;
import com.ecommerce.service.TrackingService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {
    @Test
    void shouldCreateAnOrderAndReduceStock() {
        CustomerRepository customerRepository = new CustomerRepository();
        ProductRepository productRepository = new ProductRepository();
        OrderRepository orderRepository = new OrderRepository();
        CouponRepository couponRepository = new CouponRepository();
        GiftCardRepository giftCardRepository = new GiftCardRepository();
        SubscriptionRepository subscriptionRepository = new SubscriptionRepository();

        CustomerService customerService = new CustomerService(customerRepository);
        ProductService productService = new ProductService(productRepository);
        CartService cartService = new CartService();
        DiscountService discountService = new DiscountService(couponRepository);
        GiftCardService giftCardService = new GiftCardService(giftCardRepository);
        TrackingService trackingService = new TrackingService();
        SubscriptionService subscriptionService = new SubscriptionService(subscriptionRepository, customerService);
        OrderService orderService = new OrderService(
                customerService,
                productService,
                cartService,
                orderRepository,
                trackingService,
                discountService,
                giftCardService,
                new PaymentService(),
                subscriptionService);

        Customer customer = customerService.registerCustomer("Bhavya", "bhavya@example.com", "secret123", "9988776655",
                new Address("10 River Street", "Delhi", "Delhi", "110001"));
        Product product = productService.addProduct("Keyboard", "Logitech", "Mechanical keyboard", new BigDecimal("2500"), 10);

        cartService.addToCart(customer.getCustomerId(), product, 2);
        productService.updateProduct(product.getProductId(), null, null, null, new BigDecimal("2750"), null);
        var order = orderService.checkout(customer.getCustomerId(), new CashOnDelivery(), null, null);

        assertTrue(order.getOrderId().matches("ORD\\d+"));
        assertSame(order, orderRepository.findById(order.getOrderId()).orElseThrow());
        assertEquals(customer.getCustomerId(), order.getCustomerId());
        assertEquals(1, orderRepository.findByCustomerId(customer.getCustomerId()).size());
        assertEquals(8, productService.getProductById(product.getProductId()).getStock());
        assertEquals(new BigDecimal("2750"), order.getItems().get(0).getUnitPrice());
        assertEquals(com.ecommerce.enums.PaymentStatus.PENDING, order.getPayment().getStatus());
        assertNull(order.getTrackingInfo(), "Tracking is not available until admin dispatches the order");
    }

    @Test
    void failedPaymentKeepsCartAndInventoryUnchanged() {
        CustomerRepository customerRepository = new CustomerRepository();
        ProductRepository productRepository = new ProductRepository();
        OrderRepository orderRepository = new OrderRepository();
        CustomerService customerService = new CustomerService(customerRepository);
        ProductService productService = new ProductService(productRepository);
        CartService cartService = new CartService();
        OrderService orderService = new OrderService(customerService, productService, cartService,
                orderRepository, new TrackingService(), new DiscountService(new CouponRepository()),
                new GiftCardService(new GiftCardRepository()), new PaymentService(),
                new SubscriptionService(new SubscriptionRepository(), customerService));

        Customer customer = customerService.registerCustomer("Ravi", "ravi@example.com", "secret123",
                "9988776655", new Address("10 River Street", "Delhi", "Delhi", "110001"));
        Product product = productService.addProduct("Mouse", "Logitech", "Wireless mouse",
                new BigDecimal("1000"), 4);
        cartService.addToCart(customer.getCustomerId(), product, 2);

        assertThrows(com.ecommerce.exception.PaymentFailedException.class, () ->
                orderService.checkout(customer.getCustomerId(), new UpiPayment("simulated", false), null, null));
        assertEquals(4, productService.getProductById(product.getProductId()).getStock());
        assertFalse(cartService.getCart(customer.getCustomerId()).isEmpty());
        assertTrue(orderRepository.findByCustomerId(customer.getCustomerId()).isEmpty());
    }

    @Test
    void orderStatusAndTrackingRequireValidTransitions() {
        CustomerRepository customerRepository = new CustomerRepository();
        ProductRepository productRepository = new ProductRepository();
        OrderRepository orderRepository = new OrderRepository();
        CustomerService customerService = new CustomerService(customerRepository);
        ProductService productService = new ProductService(productRepository);
        CartService cartService = new CartService();
        TrackingService trackingService = new TrackingService();
        OrderService orderService = new OrderService(customerService, productService, cartService,
                orderRepository, trackingService, new DiscountService(new CouponRepository()),
                new GiftCardService(new GiftCardRepository()), new PaymentService(),
                new SubscriptionService(new SubscriptionRepository(), customerService));
        Customer customer = customerService.registerCustomer("Nila", "nila@example.com", "secret123",
                "9988776654", new Address("10 Lake Road", "Delhi", "Delhi", "110001"));
        Product product = productService.addProduct("Headphones", "JBL", "Wireless headphones",
                new BigDecimal("2000"), 3);
        cartService.addToCart(customer.getCustomerId(), product, 1);
        var order = orderService.checkout(customer.getCustomerId(), new CashOnDelivery(), null, null);

        assertThrows(com.ecommerce.exception.InvalidOrderStateException.class, () ->
                orderService.updateOrderStatus(order.getOrderId(), com.ecommerce.enums.OrderStatus.DELIVERED));
        orderService.assignTracking(order.getOrderId(), "ParcelCo", "TRACK-1");
        orderService.updateOrderStatus(order.getOrderId(), com.ecommerce.enums.OrderStatus.IN_TRANSIT);
        assertEquals(com.ecommerce.enums.PaymentStatus.PENDING, order.getPayment().getStatus());
        orderService.updateOrderStatus(order.getOrderId(), com.ecommerce.enums.OrderStatus.DELIVERED);
        assertEquals(com.ecommerce.enums.PaymentStatus.SUCCESS, order.getPayment().getStatus());
        orderService.requestReturn(customer.getCustomerId(), order.getOrderId());
        orderService.updateOrderStatus(order.getOrderId(), com.ecommerce.enums.OrderStatus.RETURNED);
        assertEquals(3, productService.getProductById(product.getProductId()).getStock());
        assertEquals(5, order.getTrackingInfo().getEvents().size());
    }
}
