package com.ecommerce.repository;

import com.ecommerce.model.GiftCard;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GiftCardRepository {
    private final Map<String, GiftCard> cardsByCode = new HashMap<>();

    public void save(GiftCard giftCard) {
        if (giftCard == null) {
            throw new IllegalArgumentException("Gift card cannot be null");
        }
        cardsByCode.put(giftCard.getGiftCardCode().toUpperCase(), giftCard);
    }

    public Optional<GiftCard> findByCode(String code) {
        return Optional.ofNullable(cardsByCode.get(code == null ? "" : code.trim().toUpperCase()));
    }
}
