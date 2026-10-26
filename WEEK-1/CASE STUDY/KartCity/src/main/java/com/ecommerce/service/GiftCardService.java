package com.ecommerce.service;

import com.ecommerce.model.GiftCard;
import com.ecommerce.repository.GiftCardRepository;

import java.math.BigDecimal;
import java.time.LocalDate;

public class GiftCardService {
    private final GiftCardRepository giftCardRepository;

    public GiftCardService(GiftCardRepository giftCardRepository) {
        this.giftCardRepository = giftCardRepository;
    }

    public GiftCard createGiftCard(String code, BigDecimal balance, LocalDate expiryDate) {
        if (giftCardRepository.findByCode(code).isPresent()) {
            throw new IllegalArgumentException("Gift card code already exists");
        }
        GiftCard giftCard = new GiftCard(code, balance, expiryDate);
        giftCardRepository.save(giftCard);
        return giftCard;
    }

    public GiftCard getGiftCardByCode(String code) {
        return giftCardRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("Gift card not found: " + code));
    }

    public BigDecimal applyGiftCard(String code, BigDecimal amount) {
        if (code == null || code.isBlank() || amount == null) {
            return BigDecimal.ZERO;
        }

        GiftCard giftCard = giftCardRepository.findByCode(code).orElse(null);
        if (giftCard == null || !giftCard.isActive()) {
            return BigDecimal.ZERO;
        }

        BigDecimal usableAmount = giftCard.getBalance().min(amount);
        if (usableAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }
        giftCard.redeem(usableAmount);
        giftCardRepository.save(giftCard);
        return usableAmount;
    }

    public BigDecimal previewGiftCard(String code, BigDecimal amount) {
        if (code == null || code.isBlank() || amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }
        GiftCard giftCard = giftCardRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("Gift card not found: " + code));
        if (!giftCard.isActive()) {
            throw new IllegalArgumentException("Gift card is expired or inactive");
        }
        return giftCard.getBalance().min(amount);
    }

    public void redeemGiftCard(String code, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            return;
        }
        GiftCard giftCard = getGiftCardByCode(code);
        giftCard.redeem(amount);
        giftCardRepository.save(giftCard);
    }
}
