package com.enigma.zerowaste.controller;

import com.enigma.zerowaste.constant.ApiUrlConstant;
import com.enigma.zerowaste.entity.Product;
import com.enigma.zerowaste.entity.QuantityType;
import com.enigma.zerowaste.security.jwt.AuthEntryPointJwt;
import com.enigma.zerowaste.security.jwt.JwtUtils;
import com.enigma.zerowaste.security.services.CustomerDetailsServiceImpl;
import com.enigma.zerowaste.service.QuantityTypeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(QuantityTypeController.class)
class QuantityTypeControllerTest {

    @MockBean
    QuantityTypeService service;

    @Autowired
    QuantityTypeController controller;

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

    private QuantityType qType = new QuantityType();
    private List<Product> productList = new ArrayList<>();
    private QuantityType outQType;

    @BeforeEach
    void setup() {
        qType = new QuantityType("qt01", "pcs", "quantity type number 1", productList);

        service.saveQType(qType);
        outQType = qType;

        mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .apply(springSecurity())
                .build();
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void tearDown() {
        qType = null;
    }

    /*@Test
    @WithMockUser(username = "khansa", password = "khansa", roles = "ADMIN")
    void createQType() throws Exception {
        given(service.saveQType(any(qType.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));

        mockMvc.perform(post("/api/v1/zero-waste/quantity-type")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(qType)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name", Matchers.is(qType.getName())));
    }*/

    @Test
    void getAllQTypes() {
    }

//    @Test
    /*void getQTypeById() throws Exception {
        when(service.getQTypeById(qType.getId())).thenReturn(qType);

        RequestBuilder request = get("/api/v1/zero-waste/quantity-type/{qTypeId}", qType.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.user("khansa").roles("ADMIN"));
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }*/

    @Test
    void updateQType() {
    }

    @Test
    void deleteQTypeById() {
    }

    @Test
    void getQTypePerPage() {
    }
}