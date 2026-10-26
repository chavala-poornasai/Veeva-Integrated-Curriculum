package com.ecommerce.service;

import com.ecommerce.enums.SubscriptionPlan;
import com.ecommerce.model.Customer;
import com.ecommerce.model.Subscription;
import com.ecommerce.exception.PaymentFailedException;
import com.ecommerce.payment.PaymentMethod;
import com.ecommerce.payment.PaymentService;
import com.ecommerce.repository.SubscriptionRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final CustomerService customerService;
    private final PaymentService paymentService;

    public SubscriptionService(SubscriptionRepository subscriptionRepository, CustomerService customerService) {
        this(subscriptionRepository, customerService, new PaymentService());
    }

    public SubscriptionService(SubscriptionRepository subscriptionRepository, CustomerService customerService,
                               PaymentService paymentService) {
        this.subscriptionRepository = subscriptionRepository;
        this.customerService = customerService;
        this.paymentService = paymentService;
    }

    public Subscription createSubscription(String customerId, SubscriptionPlan plan) {
        Customer customer = customerService.getCustomerById(customerId);
        Subscription subscription = new Subscription(UUID.randomUUID().toString(), customer.getCustomerId(), plan, LocalDate.now());
        customer.addSubscription(subscription);
        subscriptionRepository.save(subscription);
        return subscription;
    }

    public Subscription purchaseSubscription(String customerId, SubscriptionPlan plan,
                                             PaymentMethod paymentMethod) {
        Customer customer = customerService.getCustomerById(customerId);
        if (plan == null) {
            throw new IllegalArgumentException("Subscription plan is required");
        }
        var payment = paymentService.processPayment(paymentMethod, plan.getBasePrice());
        if (payment.getStatus() != com.ecommerce.enums.PaymentStatus.SUCCESS) {
            throw new PaymentFailedException("Subscription payment failed; no subscription was created.");
        }
        Subscription subscription = new Subscription(UUID.randomUUID().toString(), customer.getCustomerId(),
                plan, LocalDate.now(), payment);
        customer.addSubscription(subscription);
        subscriptionRepository.save(subscription);
        return subscription;
    }

    public boolean isCustomerSubscribed(String customerId) {
        return subscriptionRepository.findByCustomerId(customerId).stream().anyMatch(Subscription::isActive);
    }

    public List<Subscription> getSubscriptions(String customerId) {
        return subscriptionRepository.findByCustomerId(customerId);
    }

    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }
}
