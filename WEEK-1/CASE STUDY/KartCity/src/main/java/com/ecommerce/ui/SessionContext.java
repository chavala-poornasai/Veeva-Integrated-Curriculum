package com.ecommerce.ui;

import com.ecommerce.model.Customer;

public class SessionContext {
    private Customer currentCustomer;
    private boolean adminAuthenticated;

    public Customer getCurrentCustomer() {
        return currentCustomer;
    }

    public boolean isCustomerAuthenticated() {
        return currentCustomer != null && currentCustomer.isActive();
    }

    public void login(Customer customer) {
        if (customer == null || !customer.isActive()) {
            throw new IllegalArgumentException("An active customer is required");
        }
        currentCustomer = customer;
    }

    public void logoutCustomer() {
        currentCustomer = null;
    }

    public boolean isAdminAuthenticated() {
        return adminAuthenticated;
    }

    public void loginAdmin() {
        adminAuthenticated = true;
    }

    public void logoutAdmin() {
        adminAuthenticated = false;
    }
}
