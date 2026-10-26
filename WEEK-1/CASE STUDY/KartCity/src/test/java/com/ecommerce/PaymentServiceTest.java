package com.ecommerce;

import com.ecommerce.enums.PaymentStatus;
import com.ecommerce.payment.CashOnDelivery;
import com.ecommerce.payment.PaymentService;
import com.ecommerce.payment.UpiPayment;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PaymentServiceTest {
    @Test
    void shouldProcessUpiPaymentSuccessfully() {
        PaymentService service = new PaymentService();
        var payment = service.processPayment(new UpiPayment("simulated", true), new BigDecimal("2500"));
        assertEquals(PaymentStatus.SUCCESS, payment.getStatus());
        assertEquals("UPI", payment.getPaymentMethod());
    }

    @Test
    void shouldHandleCashOnDelivery() {
        PaymentService service = new PaymentService();
        var payment = service.processPayment(new CashOnDelivery(true), new BigDecimal("400"));
        assertEquals(PaymentStatus.PENDING, payment.getStatus());
    }
}
