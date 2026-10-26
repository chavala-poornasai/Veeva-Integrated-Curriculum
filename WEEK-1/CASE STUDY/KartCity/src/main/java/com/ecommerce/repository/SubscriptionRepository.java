package com.ecommerce.repository;

import com.ecommerce.model.Subscription;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SubscriptionRepository {
    private final Map<String, Subscription> subscriptionsById = new HashMap<>();

    public void save(Subscription subscription) {
        if (subscription == null) {
            throw new IllegalArgumentException("Subscription cannot be null");
        }
        subscriptionsById.put(subscription.getSubscriptionId(), subscription);
    }

    public Optional<Subscription> findById(String subscriptionId) {
        return Optional.ofNullable(subscriptionsById.get(subscriptionId));
    }

    public List<Subscription> findByCustomerId(String customerId) {
        if (customerId == null || customerId.isBlank()) {
            return new ArrayList<>();
        }
        return subscriptionsById.values().stream()
                .filter(subscription -> subscription.getCustomerId().equals(customerId))
                .toList();
    }

    public List<Subscription> findAll() {
        return new ArrayList<>(subscriptionsById.values());
    }
}
