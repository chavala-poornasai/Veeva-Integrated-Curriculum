package com.ecommerce.repository;

import com.ecommerce.model.DiscountCoupon;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CouponRepository {
    private final Map<String, DiscountCoupon> couponsByCode = new HashMap<>();

    public void save(DiscountCoupon coupon) {
        if (coupon == null) {
            throw new IllegalArgumentException("Coupon cannot be null");
        }
        couponsByCode.put(coupon.getCouponCode(), coupon);
    }

    public Optional<DiscountCoupon> findByCode(String code) {
        return Optional.ofNullable(couponsByCode.get(code == null ? "" : code.trim().toUpperCase()));
    }
}
