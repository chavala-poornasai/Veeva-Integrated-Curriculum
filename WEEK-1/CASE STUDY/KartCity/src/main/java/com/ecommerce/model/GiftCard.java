package com.ecommerce.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class GiftCard {
    private final String giftCardCode;
    private BigDecimal balance;
    private LocalDate expiryDate;
    private boolean active;

    public GiftCard(String giftCardCode, BigDecimal balance, LocalDate expiryDate) {
        if (giftCardCode == null || giftCardCode.isBlank()) {
            throw new IllegalArgumentException("Gift card code is required");
        }
        if (balance == null || balance.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Gift card balance must be positive");
        }
        if (expiryDate == null) {
            throw new IllegalArgumentException("Expiry date is required");
        }
        this.giftCardCode = giftCardCode.trim().toUpperCase();
        this.balance = balance;
        this.expiryDate = expiryDate;
        this.active = true;
    }

    public String getGiftCardCode() {
        return giftCardCode;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public boolean isActive() {
        return active && !LocalDate.now().isAfter(expiryDate);
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean canCover(BigDecimal amount) {
        return isActive() && balance.compareTo(amount) >= 0;
    }

    public BigDecimal redeem(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Redemption amount must be positive");
        }
        if (!canCover(amount)) {
            throw new IllegalStateException("Gift card cannot cover the requested amount");
        }
        balance = balance.subtract(amount);
        return balance;
    }
}
