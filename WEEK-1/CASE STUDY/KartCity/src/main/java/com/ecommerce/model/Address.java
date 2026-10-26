package com.ecommerce.model;

public class Address {
    private final String street;
    private final String city;
    private final String state;
    private final String postalCode;

    public Address(String street, String city, String state, String postalCode) {
        if (street == null || street.isBlank()) {
            throw new IllegalArgumentException("Street is required");
        }
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("City is required");
        }
        this.street = street.trim();
        this.city = city.trim();
        this.state = state == null ? "" : state.trim();
        this.postalCode = postalCode == null ? "" : postalCode.trim();
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    @Override
    public String toString() {
        return street + ", " + city + (state.isBlank() ? "" : ", " + state) +
                (postalCode.isBlank() ? "" : ", " + postalCode);
    }
}
