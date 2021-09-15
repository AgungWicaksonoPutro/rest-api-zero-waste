package com.enigma.zerowaste.service;

import com.enigma.zerowaste.dto.CustomerSearchDTO;
import com.enigma.zerowaste.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {
    public Customer saveCustomer(Customer customer);
    public Customer getCustomerById(String id);
    public Customer updateCustomer(String id, Customer customer);
    public Customer deleteCustomer(String id);
    public Page<Customer> getCustomerPerPage(Pageable pageable, CustomerSearchDTO customerSearchDTO);
    public boolean userNameExist(String username);
    public boolean emailExist(String email);
}
