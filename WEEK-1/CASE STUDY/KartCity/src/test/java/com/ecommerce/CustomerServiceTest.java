package com.ecommerce;

import com.ecommerce.model.Address;
import com.ecommerce.model.Customer;
import com.ecommerce.repository.CustomerRepository;
import com.ecommerce.service.CustomerService;
import com.ecommerce.service.AuthenticationService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest {
    @Test
    void shouldRegisterAndFetchCustomer() {
        CustomerRepository repository = new CustomerRepository();
        CustomerService service = new CustomerService(repository);

        Customer customer = service.registerCustomer("Asha", "asha@example.com", "secret123", "9876543210",
                new Address("5 Main Road", "Hyderabad", "Telangana", "500001"));

        assertEquals("asha@example.com", customer.getEmail());
        assertEquals("CUST1001", customer.getCustomerId());
        assertEquals("Asha", service.getCustomerByEmail("asha@example.com").getName());
        assertEquals(1, service.searchByCity("Hyderabad").size());
        assertTrue(new AuthenticationService().authenticate(customer, "secret123"));
        assertFalse(new AuthenticationService().authenticate(customer, "wrong-pass"));
        service.updateCustomer(customer.getCustomerId(), null, "asha.new@example.com", null, null);
        assertTrue(service.searchByEmail("asha@example.com").isEmpty());
        assertEquals(customer.getCustomerId(), service.getCustomerByEmail("asha.new@example.com").getCustomerId());
        assertThrows(IllegalArgumentException.class, () ->
                service.registerCustomer("Another", "ASHA.NEW@example.com", "secret123", "9876543211",
                        new Address("6 Main Road", "Hyderabad", "Telangana", "500001")));
        service.deleteCustomer(customer.getCustomerId());
        assertThrows(com.ecommerce.exception.CustomerNotFoundException.class,
                () -> service.getCustomerById(customer.getCustomerId()));
        Customer nextCustomer = service.registerCustomer("Another", "another@example.com", "secret123",
                "9876543211", new Address("6 Main Road", "Hyderabad", "Telangana", "500001"));
        assertEquals("CUST1002", nextCustomer.getCustomerId());
    }
}
