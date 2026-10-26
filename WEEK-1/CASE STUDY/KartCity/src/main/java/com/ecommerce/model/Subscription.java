package com.ecommerce.model;

import com.ecommerce.enums.SubscriptionPlan;
import com.ecommerce.util.IdGenerator;

import java.time.LocalDate;

public class Subscription {
    private final String subscriptionId;
    private final String customerId;
    private final SubscriptionPlan plan;
    private LocalDate startDate;
    private LocalDate expiryDate;
    private boolean active;
    private Payment payment;

    public Subscription(String subscriptionId, String customerId, SubscriptionPlan plan, LocalDate startDate) {
        this(subscriptionId, customerId, plan, startDate, null);
    }

    public Subscription(String subscriptionId, String customerId, SubscriptionPlan plan, LocalDate startDate,
                        Payment payment) {
        if (subscriptionId == null || subscriptionId.isBlank()) {
            throw new IllegalArgumentException("Subscription ID is required");
        }
        IdGenerator.validateId(customerId, "CUST", "Customer");
        if (plan == null) {
            throw new IllegalArgumentException("Subscription plan is required");
        }
        if (startDate == null) {
            throw new IllegalArgumentException("Start date is required");
        }
        this.subscriptionId = subscriptionId;
        this.customerId = customerId;
        this.plan = plan;
        this.startDate = startDate;
        this.expiryDate = startDate.plusMonths(plan.getMonths());
        this.active = true;
        this.payment = payment;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public SubscriptionPlan getPlan() {
        return plan;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public Payment getPayment() {
        return payment;
    }

    public boolean isActive() {
        return active && !LocalDate.now().isAfter(expiryDate);
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void renew(LocalDate newStartDate) {
        this.startDate = newStartDate;
        this.expiryDate = newStartDate.plusMonths(plan.getMonths());
        this.active = true;
    }
}
