package com.enigma.zerowaste.controller;

import com.enigma.zerowaste.constant.ResponseMessage;
import com.enigma.zerowaste.dto.shoppingbag.AddToShoppingBagDTO;
import com.enigma.zerowaste.dto.shoppingbag.ShoppingBagDTO;
import com.enigma.zerowaste.entity.Address;
import com.enigma.zerowaste.entity.Customer;
import com.enigma.zerowaste.entity.Product;
import com.enigma.zerowaste.entity.ShoppingBag;
import com.enigma.zerowaste.exception.DataNotFoundException;
import com.enigma.zerowaste.security.WebSecurityConfig;
import com.enigma.zerowaste.security.jwt.AuthEntryPointJwt;
import com.enigma.zerowaste.security.jwt.JwtUtils;
import com.enigma.zerowaste.security.services.CustomerDetailsServiceImpl;
import com.enigma.zerowaste.service.ShoppingBagService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.models.Model;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShoppingBagController.class)
class ShoppingBagControllerTest {

    @MockBean
    ShoppingBagService shoppingBagService;

    @Autowired
    ShoppingBagController shoppingBagController;

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
    JwtUtils jwtUtils;



    private AddToShoppingBagDTO addToShoppingBagDTO;
    private ShoppingBag shoppingBag;
    private Customer customer;
    private Product product;
    private ShoppingBag output;
    private ModelMapper modelMapper;
    private ShoppingBagDTO shoppingBagDTO;

    @BeforeEach
    void setup(){
        shoppingBag = new ShoppingBag();
        shoppingBag.setId("idShoppingBag");
        customer = new Customer();
        customer.setId("idCustomer");
        product = new Product();
        product.setId("idProduct");
        shoppingBag.setCustomer(customer);
        shoppingBag.setProduct(product);
        shoppingBag.setAmount(9);
        output = shoppingBag;
        shoppingBagDTO = new ShoppingBagDTO();

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
    void createShoppingBag() throws Exception {
        AddToShoppingBagDTO addToShoppingBagDTO = new AddToShoppingBagDTO(product.getId(),shoppingBag.getAmount());

        when(shoppingBagService.addToShoppingBag(any())).thenReturn(shoppingBag);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/zero-waste/shopping-bag")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(SecurityMockMvcRequestPostProcessors.user("herbet").roles("USER", "ADMIN"))
                .content(asJsonString(addToShoppingBagDTO)).accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated());

    }

    @Test
    void updateShoppingBag() throws Exception {
        AddToShoppingBagDTO addToShoppingBagDTO = new AddToShoppingBagDTO(product.getId(),shoppingBag.getAmount());
        given(shoppingBagService.addToShoppingBag(any(AddToShoppingBagDTO.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/zero-waste/shopping-bag/{id}",shoppingBag.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(SecurityMockMvcRequestPostProcessors.user("herbet").roles("USER", "ADMIN"))
                .content(asJsonString(addToShoppingBagDTO)).accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }

    @Test
    void deleteShoppingBag() throws Exception {
        String shoppingBagId = shoppingBag.getId();

        doNothing().when(shoppingBagService).deleteShoppingBag(shoppingBagId);

        mockMvc.perform(
                delete("/api/v1/zero-waste/shopping-bag/{idShoppingBag}", shoppingBagId)
                        .with(SecurityMockMvcRequestPostProcessors.user("herbet").roles("USER", "ADMIN")))
                .andExpect(status().isOk());
    }

    @Test
    void getAllShoppingBag() throws Exception {

        when(shoppingBagService.getAllShoppingBag()).thenReturn(shoppingBagDTO);

        RequestBuilder requestBuilder = get("/api/v1/zero-waste/shopping-bag")
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.user("herbet").roles("USER", "ADMIN"));

        //expected
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}