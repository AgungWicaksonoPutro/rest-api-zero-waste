package com.enigma.zerowaste.service.impl;

import com.enigma.zerowaste.constant.ResponseMessage;
import com.enigma.zerowaste.controller.CustomerController;
import com.enigma.zerowaste.dto.CustomerSearchDTO;
import com.enigma.zerowaste.entity.Address;
import com.enigma.zerowaste.entity.Customer;
import com.enigma.zerowaste.entity.ERole;
import com.enigma.zerowaste.entity.Role;
import com.enigma.zerowaste.exception.DataNotFoundException;
import com.enigma.zerowaste.repository.CustomerRepository;
import com.enigma.zerowaste.repository.RoleRepository;
import com.enigma.zerowaste.service.AddressService;
import com.enigma.zerowaste.service.CustomerService;
import com.enigma.zerowaste.service.RoleService;
import com.enigma.zerowaste.specification.CustomerSpesification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AddressService addressService;

    @Autowired
    RoleService roleService;



    @Override
    public Customer saveCustomer(Customer customer) {
        Set<String> strRoles = new HashSet<>();
        for (Role role: customer.getRoles()){
            strRoles.add(role.getName().name());
        }
        Set<Role> roles = new HashSet<>();
        String message = ResponseMessage.ERROR_MESSAGE_ROLE;
        if (strRoles == null) {
            Role userRole = roleService.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException(message));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "ROLE_ADMIN":
                        Role adminRole = roleService.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException(message));
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleService.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException(message));
                        roles.add(userRole);
                }
            });
        }
        customer.setRoles(roles);
        Customer customerSaved = customerRepository.save(customer);

        for (Address address : customer.getAddresses()) {
            address.setCustomer(customerSaved);
            addressService.saveAddress(address);
        }
        return customerSaved;
    }

    @Override
    public Customer getCustomerById(String id) {
        validatePresent(id);
        return customerRepository.findById(id).get();
    }

    @Override
    public Customer updateCustomer(String id, Customer customer) {
        Customer customerUpdated = getCustomerById(id);
        customer.setId(id);
        customerUpdated = customerRepository.save(customer);

        for (Address address : customer.getAddresses()){
            address.setCustomer(customerUpdated);
            addressService.saveAddress(address);
        }
        return customerUpdated;
    }


    @Override
    public Customer deleteCustomer(String id) {
        Customer customerDeleted = getCustomerById(id);
        customerDeleted.setIsDeleted(true);
        customerRepository.save(customerDeleted);
        for (Address address : customerDeleted.getAddresses()) {
            address.setCustomer(customerDeleted);
            address.setIsdeleted(true);
            addressService.saveAddress(address);
        }
        return customerDeleted;
    }

    @Override
    public Page<Customer> getCustomerPerPage(Pageable pageable, CustomerSearchDTO customerSearchDTO) {
        Specification<Customer> customerSpecification = CustomerSpesification.getSpesification(customerSearchDTO);
        return customerRepository.findAll(customerSpecification, pageable);
    }

    private void validatePresent(String id) {
        if (!customerRepository.findById(id).isPresent()) {
            String message = String.format(ResponseMessage.NOT_FOUND_MESSAGE, "Address", id);
            throw new DataNotFoundException(message);
        }
    }

    public boolean userNameExist(String username) {
        return customerRepository.existsByUserName(username);
    }

    public boolean emailExist(String email) {
        return customerRepository.existsByEmail(email);
    }
}
