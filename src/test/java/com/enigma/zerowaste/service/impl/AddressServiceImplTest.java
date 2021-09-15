package com.enigma.zerowaste.service.impl;

import com.enigma.zerowaste.dto.AddressDTO;
import com.enigma.zerowaste.entity.Address;
import com.enigma.zerowaste.entity.Customer;
import com.enigma.zerowaste.repository.AddressRepository;
import com.enigma.zerowaste.repository.CustomerRepository;
import com.enigma.zerowaste.security.services.CustomerDetailsImpl;
import com.enigma.zerowaste.security.services.CustomerDetailsServiceImpl;
import com.enigma.zerowaste.service.AddressService;
import com.enigma.zerowaste.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;


@SpringBootTest
@AutoConfigureMockMvc
class AddressServiceImplTest {

    @Autowired
    AddressService addressService;

    @MockBean
    AddressRepository addressRepository;

    @MockBean
    CustomerRepository customerRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CustomerDetailsServiceImpl customerDetailsService;

    private Address address;
    private AddressDTO addressDTO;
    private Address output;
    private Customer customer;
    private Authentication authentication;
    private SecurityContext securityContext;
    private SecurityContextHolder securityContextHolder;

    @BeforeEach
    void setup() {
        customer = new Customer();
        customer.setId("2348293723vhcvgccctrfrr");
        customer.setCustomerName("herbet");
        customer.setPhone("0932487324");
        customer.setUserName("herbet");
        customer.setEmail("herbet@email.com");
        customer.setPassword("12345");
        authentication = Mockito.mock(Authentication.class);
        securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        securityContextHolder.setContext(securityContext);
        CustomerDetailsImpl cdi = new CustomerDetailsImpl();
        cdi.setId(customer.getId());
        Mockito.when(securityContext.getAuthentication().getPrincipal()).thenReturn(cdi);
        address = new Address("234sdfafdasf", "jalan kenangan", "082347323", "ini adalah jalan kenangan", customer, false);
        System.out.println(customer);
        output = new Address();
        output.setId(address.getId());
        output.setName(address.getName());
        output.setPhoneNumber(address.getPhoneNumber());
        output.setDescriptions(address.getDescriptions());
        output.setCustomer(address.getCustomer());
        output.setIsdeleted(address.getIsdeleted());
        when(addressRepository.save(any())).thenReturn(output);
    }

    @Test
    void saveAddress() {
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.ofNullable(customer));
        address.setCustomer(customer);
        Address a = addressService.saveAddress(address);
        assertEquals(output, a);
    }

    @Test
    void saveAddressDto() {
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.ofNullable(customer));
        address.setCustomer(customer);
        Address address = new Address();
        addressDTO = new AddressDTO("jalan kenangan","082347323","ini adalah jalan kenangan");
        address.setName(addressDTO.getName());
        addressDTO.setPhoneNumber(addressDTO.getPhoneNumber());
        addressDTO.setDescriptions(addressDTO.getDescriptions());
        Address a = addressService.saveAddressDto(addressDTO);
        assertEquals(output, a);

    }

    @Test
    void getAddressById() {
        addressRepository.save(address);
        given(addressRepository.existsById("234sdfafdasf")).willReturn(true);

        given(addressRepository.findById("234sdfafdasf")).willReturn(Optional.of(output));
        Address returned = addressService.getAddressById("234sdfafdasf");
        assertEquals(output, returned);
    }

    @Test
    void updateAddress() {
        when(addressRepository.findById(address.getId())).thenReturn(Optional.of(output));
        output.setId(address.getId());

        Address addressUpdated = addressService.updateAddress(address.getId(),address);
        assertEquals(output,addressUpdated);
    }

    @Test
    void deleteAddress() {
        when(addressRepository.findById(address.getId())).thenReturn(Optional.of(output));
        output.setIsdeleted(true);
        Address addressDeleted = addressService.deleteAddress(address.getId());
        assertEquals(output,addressDeleted);
    }

    @Test
    void getAddressPerPage() {
    }
}