package com.ecommerce.payment;

import com.ecommerce.model.Payment;

import java.math.BigDecimal;

public class PaymentService {
    public Payment processPayment(PaymentMethod paymentMethod, BigDecimal amount) {
        if (paymentMethod == null) {
            throw new IllegalArgumentException("Payment method is required");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            return new com.ecommerce.model.Payment(java.util.UUID.randomUUID().toString(), amount,
                    "NO_PAYMENT_REQUIRED", com.ecommerce.enums.PaymentStatus.SUCCESS);
        }
        return paymentMethod.pay(amount);
    }
}
