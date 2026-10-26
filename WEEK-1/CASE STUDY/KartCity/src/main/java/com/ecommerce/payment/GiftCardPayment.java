package com.ecommerce.payment;

import com.ecommerce.enums.PaymentStatus;
import com.ecommerce.model.GiftCard;
import com.ecommerce.model.Payment;

import java.math.BigDecimal;
import java.util.UUID;

public class GiftCardPayment implements PaymentMethod {
    private final GiftCard giftCard;
    private final boolean successful;

    public GiftCardPayment(GiftCard giftCard) {
        this(giftCard, true);
    }

    public GiftCardPayment(GiftCard giftCard, boolean successful) {
        if (giftCard == null) {
            throw new IllegalArgumentException("Gift card is required");
        }
        this.giftCard = giftCard;
        this.successful = successful;
    }

    @Override
    public Payment pay(BigDecimal amount) {
        boolean approved = successful && giftCard.canCover(amount);
        if (approved) {
            giftCard.redeem(amount);
        }
        PaymentStatus status = approved ? PaymentStatus.SUCCESS : PaymentStatus.FAILED;
        return new Payment(UUID.randomUUID().toString(), amount, getName(), status);
    }

    @Override
    public String getName() {
        return "GIFT_CARD";
    }
}
