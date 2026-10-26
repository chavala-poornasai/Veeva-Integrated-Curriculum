package com.ecommerce.payment;

import com.ecommerce.enums.PaymentStatus;
import com.ecommerce.model.Payment;

import java.math.BigDecimal;

public interface PaymentMethod {
    Payment pay(BigDecimal amount);

    String getName();

    default PaymentStatus simulatePaymentStatus() {
        return PaymentStatus.SUCCESS;
    }
}
