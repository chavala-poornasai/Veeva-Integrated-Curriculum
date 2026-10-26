package com.ecommerce;

import com.ecommerce.enums.SubscriptionPlan;
import com.ecommerce.model.Address;
import com.ecommerce.model.Customer;
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
import com.ecommerce.payment.CashOnDelivery;
import com.ecommerce.payment.PaymentService;
import com.ecommerce.payment.UpiPayment;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CommerceExtensionsTest {
    @Test
    void couponIsBoundedByEligibilityAndRedemptionLimit() {
        CouponRepository repository = new CouponRepository();
        DiscountService discounts = new DiscountService(repository);
        Customer customer = new CustomerService(new CustomerRepository()).registerCustomer(
                "Ira", "ira@example.com", "secret123", "9876543210",
                new Address("1 Main Street", "Pune", "Maharashtra", "411001"));
        discounts.createPercentageCoupon("ONCE", "One-time 10 percent", new BigDecimal("10"),
                0, new BigDecimal("100"), LocalDate.now().plusDays(1), 1);

        assertEquals(new BigDecimal("10.00"), discounts.calculateDiscount(customer, "ONCE",
                new BigDecimal("100"), 0, new BigDecimal("100")));
        discounts.recordRedemption("ONCE", customer.getCustomerId());
        assertTrue(repository.findByCode("ONCE").orElseThrow()
                .getRedeemedCustomerIds().contains(customer.getCustomerId()));
        assertThrows(IllegalArgumentException.class, () -> discounts.calculateDiscount(customer, "ONCE",
                new BigDecimal("100"), 0, new BigDecimal("100")));
        assertThrows(IllegalArgumentException.class, () -> discounts.createPercentageCoupon("ONCE", "duplicate",
                new BigDecimal("10"), 0, BigDecimal.ZERO, LocalDate.now().plusDays(1), 1));
    }

    @Test
    void giftCardValidatesBalanceAndRejectsDuplicateCodes() {
        GiftCardRepository repository = new GiftCardRepository();
        GiftCardService cards = new GiftCardService(repository);
        cards.createGiftCard("SAVE50", new BigDecimal("50"), LocalDate.now().plusDays(5));

        assertEquals(new BigDecimal("30"), cards.previewGiftCard("SAVE50", new BigDecimal("30")));
        cards.redeemGiftCard("SAVE50", new BigDecimal("30"));
        assertEquals(new BigDecimal("20"), cards.getGiftCardByCode("SAVE50").getBalance());
        assertThrows(IllegalStateException.class, () ->
                cards.getGiftCardByCode("SAVE50").redeem(new BigDecimal("21")));
        assertThrows(IllegalArgumentException.class, () ->
                cards.createGiftCard("SAVE50", new BigDecimal("10"), LocalDate.now().plusDays(5)));
    }

    @Test
    void activeSubscriptionProvidesFreeShipping() {
        CustomerRepository customerRepository = new CustomerRepository();
        ProductRepository productRepository = new ProductRepository();
        OrderRepository orderRepository = new OrderRepository();
        SubscriptionRepository subscriptions = new SubscriptionRepository();
        CustomerService customers = new CustomerService(customerRepository);
        ProductService products = new ProductService(productRepository);
        CartService carts = new CartService();
        SubscriptionService subscriptionService = new SubscriptionService(subscriptions, customers);
        OrderService orders = new OrderService(customers, products, carts, orderRepository,
                new TrackingService(), new DiscountService(new CouponRepository()),
                new GiftCardService(new GiftCardRepository()), new PaymentService(), subscriptionService);
        Customer customer = customers.registerCustomer("Uma", "uma@example.com", "secret123", "9876543211",
                new Address("2 Main Street", "Pune", "Maharashtra", "411001"));
        var product = products.addProduct("Book", "ABC", "Java book", new BigDecimal("500"), 2);
        var subscription = subscriptionService.purchaseSubscription(customer.getCustomerId(),
                SubscriptionPlan.MONTHLY, new UpiPayment("simulated", true));
        carts.addToCart(customer.getCustomerId(), product, 1);

        var order = orders.checkout(customer.getCustomerId(), new CashOnDelivery(), null, null);

        assertEquals(BigDecimal.ZERO, order.getShippingCost());
        assertEquals(new BigDecimal("500"), order.getTotalAmount());
        assertEquals(new BigDecimal("299.00"), subscription.getPayment().getAmount());
    }

    @Test
    void failedSubscriptionPaymentDoesNotCreateSubscription() {
        CustomerService customers = new CustomerService(new CustomerRepository());
        Customer customer = customers.registerCustomer("Zoya", "zoya@example.com", "secret123",
                "9876543212", new Address("3 Main Street", "Pune", "Maharashtra", "411001"));
        SubscriptionService subscriptions = new SubscriptionService(new SubscriptionRepository(), customers);

        assertThrows(com.ecommerce.exception.PaymentFailedException.class, () ->
                subscriptions.purchaseSubscription(customer.getCustomerId(), SubscriptionPlan.YEARLY,
                        new UpiPayment("simulated", false)));
        assertTrue(subscriptions.getSubscriptions(customer.getCustomerId()).isEmpty());
    }
}
