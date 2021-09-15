package com.enigma.zerowaste.controller;

import com.enigma.zerowaste.dto.AddressDTO;
import com.enigma.zerowaste.entity.Address;
import com.enigma.zerowaste.entity.Customer;
import com.enigma.zerowaste.security.jwt.AuthEntryPointJwt;
import com.enigma.zerowaste.security.jwt.JwtUtils;
import com.enigma.zerowaste.security.services.CustomerDetailsServiceImpl;
import com.enigma.zerowaste.service.AddressService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import springfox.documentation.swagger2.mappers.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.RequestEntity.put;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AddressController.class)
class AddressControllerTest {

    @MockBean
    AddressService addressService;

    @Autowired
    AddressController addressController;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    AuthEntryPointJwt authEntryPointJwt;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CustomerDetailsServiceImpl customerDetailsService;

    @MockBean
    private JwtUtils jwtUtils;

    private Address address;
    private ModelMapper modelMapper;
    private Address output;

    @BeforeEach
    void setup() {
        address = new Address();
        address.setId("Adres01");
        address.setName("addres name");
        address.setPhoneNumber("09837478473");
        address.setDescriptions("ini adalah jalan kenangan");
        Customer customer = new Customer();
        customer.setId("customer1");
        address.setCustomer(customer);

        output = address;

        mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .apply(springSecurity())
                .build();

    }

    public String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void createAddress() throws Exception{
        AddressDTO addressDTO = new AddressDTO(address.getName(),address.getPhoneNumber(),address.getDescriptions());

        when(addressService.saveAddressDto(any())).thenReturn(address);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/zero-waste/address")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(SecurityMockMvcRequestPostProcessors.user("herbet").roles("USER", "ADMIN"))
                .content(asJsonString(addressDTO)).accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated());
    }

    @Test
    void createAddressWithNullName() throws Exception{
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setPhoneNumber(address.getPhoneNumber());
        addressDTO.setDescriptions(address.getDescriptions());

        when(addressService.saveAddressDto(any())).thenReturn(address);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/zero-waste/address")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(SecurityMockMvcRequestPostProcessors.user("herbet").roles("USER", "ADMIN"))
                .content(asJsonString(addressDTO)).accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
    }
    @Test
    void getAddressById() throws Exception {
        //actual
        when(addressService.getAddressById(address.getId())).thenReturn(output);

        RequestBuilder requestBuilder = get("/api/v1/zero-waste/address/{id}", address.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.user("herbet").roles("USER", "ADMIN"));

        //expected
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

    }

    @Test
    void updateAddressById() throws Exception {
        given(addressService.saveAddress(any(Address.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/zero-waste/address/{id}",address.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(SecurityMockMvcRequestPostProcessors.user("herbet").roles("USER", "ADMIN"))
                .content(asJsonString(address)).accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());

    }

    @Test
    void deleteAddressById() throws Exception {
        String addressId = address.getId();
        when(addressService.deleteAddress(any())).thenReturn(output);

        mockMvc.perform(
                delete("/api/v1/zero-waste/address/{id}", address.getId())
                .with(SecurityMockMvcRequestPostProcessors.user("herbet").roles("USER")))
                .andExpect(status().isOk());
    }

    @Test
    void searchAddressPerPage() {
    }
}