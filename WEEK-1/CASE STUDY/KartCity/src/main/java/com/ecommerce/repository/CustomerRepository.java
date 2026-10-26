package com.ecommerce.repository;

import com.ecommerce.model.Customer;

import java.util.*;

public class CustomerRepository {
    private final Map<String, Customer> customersById = new HashMap<>();
    private final Map<String, Customer> customersByEmail = new HashMap<>();

    public void save(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        Customer idOwner = customersById.get(customer.getCustomerId());
        if (idOwner != null && idOwner != customer) {
            throw new IllegalArgumentException("Customer ID already exists: " + customer.getCustomerId());
        }
        Customer emailOwner = customersByEmail.get(customer.getEmail().toLowerCase());
        if (emailOwner != null && !emailOwner.getCustomerId().equals(customer.getCustomerId())) {
            throw new IllegalArgumentException("Email already registered");
        }
        customersByEmail.entrySet().removeIf(entry ->
                entry.getValue().getCustomerId().equals(customer.getCustomerId())
                        && !entry.getKey().equals(customer.getEmail().toLowerCase()));
        customersById.put(customer.getCustomerId(), customer);
        customersByEmail.put(customer.getEmail().toLowerCase(), customer);
    }

    public Optional<Customer> findById(String customerId) {
        return Optional.ofNullable(customersById.get(customerId));
    }

    public Optional<Customer> findByEmail(String email) {
        String normalized = email == null ? "" : email.trim().toLowerCase();
        Customer customer = customersByEmail.get(normalized);
        if (customer == null || !customer.getEmail().equalsIgnoreCase(normalized)) {
            return Optional.empty();
        }
        return Optional.of(customer);
    }

    public List<Customer> findAll() {
        return customersById.values().stream().toList();
    }

    public List<Customer> findByName(String name) {
        if (name == null || name.isBlank()) {
            return new ArrayList<>();
        }
        String normalized = name.trim().toLowerCase();
        return customersById.values().stream()
                .filter(customer -> customer.getName().toLowerCase().contains(normalized))
                .toList();
    }

    public List<Customer> findByCity(String city) {
        if (city == null || city.isBlank()) {
            return new ArrayList<>();
        }
        String normalized = city.trim().toLowerCase();
        return customersById.values().stream()
                .filter(customer -> customer.getAddress() != null && customer.getAddress().getCity().toLowerCase().contains(normalized))
                .toList();
    }

    public List<Customer> findByPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            return new ArrayList<>();
        }
        String normalized = phoneNumber.trim();
        return customersById.values().stream()
                .filter(customer -> customer.getPhoneNumber().equals(normalized))
                .toList();
    }

    public boolean deleteById(String customerId) {
        Customer removed = customersById.remove(customerId);
        if (removed != null) {
            customersByEmail.remove(removed.getEmail().toLowerCase());
            return true;
        }
        return false;
    }
}
