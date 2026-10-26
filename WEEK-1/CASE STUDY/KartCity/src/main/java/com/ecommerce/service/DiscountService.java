package com.ecommerce.service;

import com.ecommerce.model.Customer;
import com.ecommerce.model.DiscountCoupon;
import com.ecommerce.repository.CouponRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class DiscountService {
    private final CouponRepository couponRepository;

    public DiscountService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public DiscountCoupon createPercentageCoupon(String code, String description, BigDecimal percentage,
                                                long minimumOrders, BigDecimal minimumSpend,
                                                LocalDate expiryDate, int redemptionLimit) {
        return createPercentageCoupon(code, description, percentage, minimumOrders, minimumSpend,
                expiryDate, redemptionLimit, null);
    }

    public DiscountCoupon createPercentageCoupon(String code, String description, BigDecimal percentage,
                                                long minimumOrders, BigDecimal minimumSpend,
                                                LocalDate expiryDate, int redemptionLimit, BigDecimal maximumDiscount) {
        if (couponRepository.findByCode(code).isPresent()) {
            throw new IllegalArgumentException("Coupon code already exists");
        }
        DiscountCoupon coupon = new DiscountCoupon(code, description, percentage, null,
                minimumOrders, minimumSpend, expiryDate, redemptionLimit, maximumDiscount);
        couponRepository.save(coupon);
        return coupon;
    }

    public DiscountCoupon createFixedCoupon(String code, String description, BigDecimal amount,
                                           long minimumOrders, BigDecimal minimumSpend,
                                           LocalDate expiryDate, int redemptionLimit, BigDecimal maximumDiscount) {
        if (couponRepository.findByCode(code).isPresent()) {
            throw new IllegalArgumentException("Coupon code already exists");
        }
        DiscountCoupon coupon = new DiscountCoupon(code, description, null, amount,
                minimumOrders, minimumSpend, expiryDate, redemptionLimit, maximumDiscount);
        couponRepository.save(coupon);
        return coupon;
    }

    public BigDecimal calculateDiscount(Customer customer, String couponCode, BigDecimal subtotal, long completedOrders) {
        return calculateDiscount(customer, couponCode, subtotal, completedOrders, subtotal);
    }

    public BigDecimal calculateDiscount(Customer customer, String couponCode, BigDecimal orderSubtotal,
                                        long completedOrders, BigDecimal cumulativeSpend) {
        if (couponCode == null || couponCode.isBlank() || orderSubtotal == null || cumulativeSpend == null) {
            return BigDecimal.ZERO;
        }

        DiscountCoupon coupon = couponRepository.findByCode(couponCode)
                .orElseThrow(() -> new IllegalArgumentException("Coupon not found"));
        if (!coupon.isEligible(customer, completedOrders, cumulativeSpend)) {
            throw new IllegalArgumentException("Coupon is expired, exhausted, or not eligible");
        }

        BigDecimal discount = coupon.getFixedDiscount();
        if (coupon.getPercentageDiscount().compareTo(BigDecimal.ZERO) > 0) {
            discount = orderSubtotal.multiply(coupon.getPercentageDiscount())
                    .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
        }

        if (coupon.getMaximumDiscount() != null && discount.compareTo(coupon.getMaximumDiscount()) > 0) {
            discount = coupon.getMaximumDiscount();
        }
        if (discount.compareTo(orderSubtotal) > 0) {
            discount = orderSubtotal;
        }

        return discount;
    }

    public void recordRedemption(String couponCode) {
        recordRedemption(couponCode, null);
    }

    public void recordRedemption(String couponCode, String customerId) {
        if (couponCode == null || couponCode.isBlank()) {
            return;
        }
        DiscountCoupon coupon = couponRepository.findByCode(couponCode)
                .orElseThrow(() -> new IllegalArgumentException("Coupon not found: " + couponCode));
        coupon.markUsed(customerId);
        couponRepository.save(coupon);
    }
}
