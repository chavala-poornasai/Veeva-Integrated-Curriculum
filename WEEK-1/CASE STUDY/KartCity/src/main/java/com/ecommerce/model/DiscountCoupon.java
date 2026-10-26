package com.ecommerce.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class DiscountCoupon {
    private final String couponCode;
    private String description;
    private BigDecimal percentageDiscount;
    private BigDecimal fixedDiscount;
    private long minimumCompletedOrders;
    private BigDecimal minimumEligibleSpend;
    private LocalDate expiryDate;
    private int redemptionLimit;
    private int usedCount;
    private boolean active;
    private BigDecimal maximumDiscount;
    private final Set<String> redeemedCustomerIds = new HashSet<>();

    public DiscountCoupon(String couponCode, String description, BigDecimal percentageDiscount,
                         BigDecimal fixedDiscount, long minimumCompletedOrders,
                         BigDecimal minimumEligibleSpend, LocalDate expiryDate,
                         int redemptionLimit) {
        this(couponCode, description, percentageDiscount, fixedDiscount, minimumCompletedOrders,
                minimumEligibleSpend, expiryDate, redemptionLimit, null);
    }

    public DiscountCoupon(String couponCode, String description, BigDecimal percentageDiscount,
                         BigDecimal fixedDiscount, long minimumCompletedOrders,
                         BigDecimal minimumEligibleSpend, LocalDate expiryDate,
                         int redemptionLimit, BigDecimal maximumDiscount) {
        if (couponCode == null || couponCode.isBlank()) {
            throw new IllegalArgumentException("Coupon code is required");
        }
        if (percentageDiscount == null && fixedDiscount == null) {
            throw new IllegalArgumentException("Coupon must have a percentage or fixed value discount");
        }
        if (percentageDiscount != null && (percentageDiscount.compareTo(BigDecimal.ZERO) < 0 || percentageDiscount.compareTo(new BigDecimal("100")) > 0)) {
            throw new IllegalArgumentException("Percentage discount must be between 0 and 100");
        }
        if (fixedDiscount != null && fixedDiscount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Fixed discount cannot be negative");
        }
        if (expiryDate == null) {
            throw new IllegalArgumentException("Expiry date is required");
        }
        if (minimumCompletedOrders < 0 || redemptionLimit < 0) {
            throw new IllegalArgumentException("Coupon order threshold and redemption limit cannot be negative");
        }
        if (minimumEligibleSpend != null && minimumEligibleSpend.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Minimum eligible spend cannot be negative");
        }
        if (maximumDiscount != null && maximumDiscount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Maximum discount must be positive");
        }
        this.couponCode = couponCode.trim().toUpperCase();
        this.description = description == null ? "" : description.trim();
        this.percentageDiscount = percentageDiscount == null ? BigDecimal.ZERO : percentageDiscount;
        this.fixedDiscount = fixedDiscount == null ? BigDecimal.ZERO : fixedDiscount;
        this.minimumCompletedOrders = minimumCompletedOrders;
        this.minimumEligibleSpend = minimumEligibleSpend == null ? BigDecimal.ZERO : minimumEligibleSpend;
        this.expiryDate = expiryDate;
        this.redemptionLimit = redemptionLimit;
        this.maximumDiscount = maximumDiscount;
        this.usedCount = 0;
        this.active = true;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPercentageDiscount() {
        return percentageDiscount;
    }

    public BigDecimal getFixedDiscount() {
        return fixedDiscount;
    }

    public BigDecimal getMaximumDiscount() {
        return maximumDiscount;
    }

    public long getMinimumCompletedOrders() {
        return minimumCompletedOrders;
    }

    public BigDecimal getMinimumEligibleSpend() {
        return minimumEligibleSpend;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public int getRedemptionLimit() {
        return redemptionLimit;
    }

    public int getUsedCount() {
        return usedCount;
    }

    public Set<String> getRedeemedCustomerIds() {
        return Set.copyOf(redeemedCustomerIds);
    }

    public boolean isActive() {
        return active && !LocalDate.now().isAfter(expiryDate) && (redemptionLimit == 0 || usedCount < redemptionLimit);
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void markUsed() {
        markUsed(null);
    }

    public void markUsed(String customerId) {
        if (redemptionLimit > 0 && usedCount >= redemptionLimit) {
            throw new IllegalStateException("Coupon redemption limit reached");
        }
        usedCount++;
        if (customerId != null && !customerId.isBlank()) {
            redeemedCustomerIds.add(customerId);
        }
    }

    public boolean isEligible(Customer customer, long completedOrders, BigDecimal totalSpend) {
        if (customer == null || !isActive()) {
            return false;
        }
        if (completedOrders < minimumCompletedOrders) {
            return false;
        }
        if (totalSpend == null) {
            return false;
        }
        return totalSpend.compareTo(minimumEligibleSpend) >= 0;
    }
}
