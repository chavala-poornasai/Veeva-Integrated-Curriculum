package com.ecommerce.payment;

import com.ecommerce.enums.PaymentStatus;
import com.ecommerce.model.Payment;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class UpiPayment implements PaymentMethod {
    private final boolean successful;

    public UpiPayment(String ignoredSimulationLabel) {
        this(ignoredSimulationLabel, ThreadLocalRandom.current().nextBoolean());
    }

    public UpiPayment(String ignoredSimulationLabel, boolean successful) {
        this.successful = successful;
    }

    @Override
    public Payment pay(BigDecimal amount) {
        PaymentStatus status = successful ? PaymentStatus.SUCCESS : PaymentStatus.FAILED;
        return new Payment(UUID.randomUUID().toString(), amount, getName(), status);
    }

    @Override
    public String getName() {
        return "UPI";
    }
}
