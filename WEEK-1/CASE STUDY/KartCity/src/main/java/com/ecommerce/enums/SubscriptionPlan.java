package com.ecommerce.enums;

import java.math.BigDecimal;

public enum SubscriptionPlan {
    MONTHLY(1, "299.00"),
    QUARTERLY(3, "799.00"),
    HALF_YEARLY(6, "1499.00"),
    YEARLY(12, "2499.00");

    private final int months;
    private final BigDecimal basePrice;

    SubscriptionPlan(int months, String basePrice) {
        this.months = months;
        this.basePrice = new BigDecimal(basePrice);
    }

    public int getMonths() {
        return months;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }
}
