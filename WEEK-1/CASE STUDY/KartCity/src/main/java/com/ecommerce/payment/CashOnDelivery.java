package com.ecommerce.payment;

import com.ecommerce.enums.PaymentStatus;
import com.ecommerce.model.Payment;

import java.math.BigDecimal;
import java.util.UUID;

public class CashOnDelivery implements PaymentMethod {
    private final boolean successful;

    public CashOnDelivery() {
        this(true);
    }

    public CashOnDelivery(boolean successful) {
        this.successful = successful;
    }

    @Override
    public Payment pay(BigDecimal amount) {
        PaymentStatus status = successful ? PaymentStatus.PENDING : PaymentStatus.FAILED;
        return new Payment(UUID.randomUUID().toString(), amount, getName(), status);
    }

    @Override
    public String getName() {
        return "CASH_ON_DELIVERY";
    }
}
