package com.ecommerce.service;

import com.ecommerce.exception.CustomerNotFoundException;
import com.ecommerce.model.Address;
import com.ecommerce.model.Customer;
import com.ecommerce.repository.CustomerRepository;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.util.IdGenerator;

import java.util.List;
import java.util.Optional;

public class CustomerService {
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final IdGenerator customerIdGenerator;

    public CustomerService(CustomerRepository customerRepository) {
        this(customerRepository, null, new IdGenerator("CUST", 1001));
    }

    public CustomerService(CustomerRepository customerRepository, OrderRepository orderRepository) {
        this(customerRepository, orderRepository, new IdGenerator("CUST", 1001));
    }

    public CustomerService(CustomerRepository customerRepository, OrderRepository orderRepository,
                           IdGenerator customerIdGenerator) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.customerIdGenerator = customerIdGenerator;
    }

    public synchronized Customer registerCustomer(String name, String email, String password, String phoneNumber, Address address) {
        if (email == null || !email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            throw new IllegalArgumentException("Enter a valid email address");
        }
        if (!isValidPhone(phoneNumber)) {
            throw new IllegalArgumentException("Enter a valid phone number");
        }
        if (customerRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }
        String passwordHash = AuthenticationService.hashPassword(password);
        String customerId = customerIdGenerator.nextId(customerRepository.findAll().stream()
                .map(Customer::getCustomerId)
                .toList());
        Customer customer = new Customer(customerId, name, email, passwordHash, phoneNumber, address);
        customerRepository.save(customer);
        return customer;
    }

    public Customer getCustomerById(String customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + customerId));
    }

    public Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with email: " + email));
    }

    public Optional<Customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public List<Customer> searchByName(String name) {
        return customerRepository.findByName(name);
    }

    public List<Customer> searchByCity(String city) {
        return customerRepository.findByCity(city);
    }

    public List<Customer> searchByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneNumber(phoneNumber);
    }

    public List<Customer> searchByEmail(String email) {
        return customerRepository.findByEmail(email)
                .map(List::of)
                .orElse(List.of());
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer updateCustomer(String customerId, String name, String email, String phoneNumber, Address address) {
        Customer customer = getCustomerById(customerId);
        if (email != null && !email.isBlank()) {
            if (!email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
                throw new IllegalArgumentException("Enter a valid email address");
            }
            customerRepository.findByEmail(email)
                    .ifPresent(existing -> {
                        if (!existing.getCustomerId().equals(customerId)) {
                            throw new IllegalArgumentException("Email already registered");
                        }
                    });
        }
        if (phoneNumber != null && !isValidPhone(phoneNumber)) {
            throw new IllegalArgumentException("Enter a valid phone number");
        }

        if (name != null && !name.isBlank()) {
            customer.setName(name);
        }
        if (email != null && !email.isBlank()) {
            customer.setEmail(email);
        }
        if (phoneNumber != null) {
            customer.setPhoneNumber(phoneNumber);
        }
        if (address != null) {
            customer.setAddress(address);
        }
        customerRepository.save(customer);
        return customer;
    }

    public void deleteCustomer(String customerId) {
        if (orderRepository != null && !orderRepository.findByCustomerId(customerId).isEmpty()) {
            throw new IllegalArgumentException("Customers with order history cannot be deleted");
        }
        if (!customerRepository.deleteById(customerId)) {
            throw new CustomerNotFoundException("Customer not found with id: " + customerId);
        }
    }

    public Customer updatePassword(String customerId, String newPassword) {
        Customer customer = getCustomerById(customerId);
        customer.setPasswordHash(AuthenticationService.hashPassword(newPassword));
        customerRepository.save(customer);
        return customer;
    }

    private boolean isValidPhone(String phoneNumber) {
        if (phoneNumber == null || !phoneNumber.matches("[0-9+() -]{7,20}")) {
            return false;
        }
        long digits = phoneNumber.chars().filter(Character::isDigit).count();
        return digits >= 7 && digits <= 15;
    }
}
