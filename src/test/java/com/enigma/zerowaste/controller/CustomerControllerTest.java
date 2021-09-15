package com.enigma.zerowaste.controller;

import com.enigma.zerowaste.entity.Address;
import com.enigma.zerowaste.entity.Customer;
import com.enigma.zerowaste.entity.ERole;
import com.enigma.zerowaste.entity.Role;
import com.enigma.zerowaste.security.jwt.AuthEntryPointJwt;
import com.enigma.zerowaste.security.jwt.JwtUtils;
import com.enigma.zerowaste.security.services.CustomerDetailsServiceImpl;
import com.enigma.zerowaste.service.CustomerService;
import com.enigma.zerowaste.service.RoleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import springfox.documentation.swagger2.mappers.ModelMapper;


import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @MockBean
    ServletException servletException;

    @MockBean
    CustomerService customerService;

    @Autowired
    CustomerController customerController;

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
    RoleService roleService;

    @MockBean
    private JwtUtils jwtUtils;


    private Customer customer;
    private ModelMapper modelMapper;
    private Customer output;
    private List<Address> address = new ArrayList<>();
    private Set<Role> roles = new HashSet<>();
    private Role role;

    @BeforeEach
    void setup() {
        customer = new Customer("abc12345", "customerName", "sdaf923823", "userName",
                "herbet@gmail.com", "herbet", false, false, address, roles);
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
    void createCustomer() throws Exception {
        roles.clear();
        role = new Role();
        role.setId(1);
        role.setName(ERole.ROLE_USER);
        roles.add(role);
        customer.setRoles(roles);
        when(customerService.saveCustomer(any(Customer.class))).thenReturn(customer);
        when(roleService.findByName(any())).thenReturn(java.util.Optional.ofNullable(role));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/zero-waste/customers")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(SecurityMockMvcRequestPostProcessors.user("herbet").roles("USER", "ADMIN"))
                .content(asJsonString(customer)).accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated());
    }

    @Test
    void getCustomerById() throws Exception {
        when(customerService.getCustomerById(customer.getId())).thenReturn(output);

        RequestBuilder requestBuilder = get("/api/v1/zero-waste/customers/{id}", customer.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.user("herbet").roles("USER", "ADMIN"));

        //expected
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void updateCustomer() throws Exception {
        given(customerService.saveCustomer(any(Customer.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/zero-waste/customers/{id}", customer.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(SecurityMockMvcRequestPostProcessors.user("herbet").roles("USER", "ADMIN"))
                .content(asJsonString(customer)).accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }

    @Test
    void deleteCustomer() throws Exception {
        String addressId = customer.getId();
        when(customerService.deleteCustomer(any())).thenReturn(output);

        mockMvc.perform(
                delete("/api/v1/zero-waste/customers/{id}", customer.getId())
                        .with(SecurityMockMvcRequestPostProcessors.user("herbet").roles("USER", "ADMIN")))
                .andExpect(status().isOk());
    }

    @Test
    void searchCustomerPerPage() {
    }
}