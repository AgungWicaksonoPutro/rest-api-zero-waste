package com.enigma.zerowaste.service.impl;

import com.enigma.zerowaste.entity.*;
import com.enigma.zerowaste.repository.AddressRepository;
import com.enigma.zerowaste.repository.CustomerRepository;
import com.enigma.zerowaste.security.services.CustomerDetailsImpl;
import com.enigma.zerowaste.service.AddressService;
import com.enigma.zerowaste.service.CustomerService;
import com.enigma.zerowaste.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class CustomerServiceImplTest {

    @Autowired
    CustomerService customerService;

    @MockBean
    CustomerRepository customerRepository;

    @Autowired
    RoleService roleService;

    @Autowired
    AddressService addressService;

    @MockBean
    AddressRepository addressRepository;


    private Customer customer;
    private Customer output;
    private List<Address> address = new ArrayList<>();
    private Set<Role> roles = new HashSet<>();
    private Role role;
    private Authentication authentication;
    private SecurityContext securityContext;
    private SecurityContextHolder securityContextHolder;

    @BeforeEach
    void setup(){
//        role = new Role();
//        role.setId(1);
//        role.setName(ERole.ROLE_USER);
//
//        roles.add(role);

        customer = new Customer("abc12345","customerName","sdaf923823","userName",
                "herbet@gmail.com","herbet",false,false,address,roles);
        output = new Customer();
        output.setId(customer.getId());
        output.setCustomerName(customer.getCustomerName());
        output.setPhone(customer.getPhone());
        output.setUserName(customer.getUserName());
        output.setEmail(customer.getEmail());
        output.setPassword(customer.getPassword());
        output.setRoles(customer.getRoles());
        output.setAddresses(customer.getAddresses());
        output.setEmailVerfied(customer.getEmailVerfied());
        when(customerRepository.save(any())).thenReturn(output);
        authentication = Mockito.mock(Authentication.class);
        securityContext = Mockito.mock(SecurityContext.class);
        //yang baru ditambahin
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        securityContextHolder.setContext(securityContext);
        CustomerDetailsImpl cdi = new CustomerDetailsImpl();
        cdi.setId(output.getId());
        Mockito.when(securityContext.getAuthentication().getPrincipal()).thenReturn(cdi);
    }



    @Test
    void saveCustomer() {
        Customer newCustomer = customerService.saveCustomer(customer);
        assertEquals(output,newCustomer);
    }

    @Test
    void saveCustomerRoleAdmin() {
        roles.clear();
        role = new Role();
        role.setId(2);
        role.setName(ERole.ROLE_ADMIN);
        roles.add(role);
        customer.setRoles(roles);
        Customer newCustomer = customerService.saveCustomer(customer);
        assertEquals(output,newCustomer);
    }
    @Test
    void saveCustomerWithAddress(){
        Address addres1 = new Address();
        addres1.setId("idAddres");
        addres1.setName("addresName");
        address.add(addres1);

        when(addressRepository.save(any())).thenReturn(addres1);
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.ofNullable(output));

        customer.setAddresses(address);

        output.setAddresses(address);

        customer.setAddresses(address);
        Customer newCustomer = customerService.saveCustomer(customer);
        assertEquals(output,newCustomer);
    }


    @Test
    void getCustomerById() {
        Customer customer = new Customer();
        customer.setId("id");
        customer.setCustomerName("herbet");
        customer.setPhone("0812345693");
        customer.setUserName("herbet");
        customer.setEmail("herbetsimanjuntak@gmail.com");
        customer.setPassword("12345");
        customer.setEmailVerfied(false);
        customer.setIsDeleted(false);

        Customer newCustomer = new Customer();
        customerRepository.save(customer);
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        newCustomer.setId(customerService.getCustomerById("id").getId());
        newCustomer.setCustomerName(customerService.getCustomerById("id").getCustomerName());
        newCustomer.setEmail(customerService.getCustomerById("id").getEmail());

        assertEquals(customer.getId(),newCustomer.getId());
        assertEquals(customer.getCustomerName(),newCustomer.getCustomerName());
        assertEquals(customer.getEmail(),newCustomer.getEmail());

    }

    @Test
    void deleteCustomer() {

        Address a = new Address();
        a.setId("jalanabc123");
        a.setName("jalan jalan");
        a.setPhoneNumber("01233843");
        a.setDescriptions("ini jalan");
        a.setIsdeleted(true);
        when(addressRepository.save(any())).thenReturn(a);

        address.add(a);
        output.setAddresses(address);
        //expected
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.ofNullable(output));
        output.setIsDeleted(true);
        //hasil dari proses customer delted ->actual
        Customer customerDeleted = customerService.deleteCustomer(customer.getId());
        assertEquals(output,customerDeleted);
    }

    @Test
    void updateCustomer(){
        Address a = new Address();
        a.setId("jalanabc123");
        a.setName("jalan jalan");
        a.setPhoneNumber("01233843");
        a.setDescriptions("ini jalan");
        a.setIsdeleted(true);
        when(addressRepository.save(any())).thenReturn(a);

        address.add(a);
        output.setAddresses(address);
        //expected
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.ofNullable(output));
        output.setId(customer.getId());

        //actual
        Customer customerUpdeted = customerService.updateCustomer(customer.getId(),customer);
        assertEquals(output,customerUpdeted);
    }


    @Test
    void getCustomerPerPage() {
    }

    @Test
    void userNameExist() {
        Boolean actual = true;
        when(customerRepository.existsByUserName(customer.getUserName())).thenReturn(actual);

        Boolean expected = customerService.userNameExist(output.getUserName());

        assertEquals(expected,actual);
    }

    @Test
    void emailExist() {
        Boolean actual = true;
        when(customerRepository.existsByEmail(customer.getEmail())).thenReturn(actual);

        Boolean expected = customerService.emailExist(output.getEmail());

        assertEquals(expected,true);
    }
}