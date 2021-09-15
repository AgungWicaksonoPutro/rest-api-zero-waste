package com.enigma.zerowaste.controller;

import com.enigma.zerowaste.entity.Brand;
import com.enigma.zerowaste.security.WebSecurityConfig;
import com.enigma.zerowaste.security.jwt.AuthEntryPointJwt;
import com.enigma.zerowaste.security.jwt.JwtUtils;
import com.enigma.zerowaste.security.services.CustomerDetailsImpl;
import com.enigma.zerowaste.security.services.CustomerDetailsServiceImpl;
import com.enigma.zerowaste.service.BrandService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BrandController.class)
class BrandControllerTest {

    @MockBean
    private BrandService brandService;

    @MockBean
    AuthEntryPointJwt authEntryPointJwt;

    @MockBean
    CustomerDetailsServiceImpl customerDetailsService;

    @Autowired
    WebSecurityConfig webSecurityConfig;

    @MockBean
    JwtUtils jwtUtils;


    private Brand brand;
    private List<Brand> brands;

    @Autowired
    private BrandController brandController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;



    @BeforeEach
    void setUp() {
        brand = new Brand("B01", "Sunslick", "Product kebersihan Rambut");
    }

    public static String asJsonString(final Object obj){
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void tearDown() {
        brand = null;
    }

    @Test
    @WithMockUser(username = "agung", password = "agung", roles = "ADMIN")
    void createBrand() throws Exception{
        given(brandService.saveBrand(any(Brand.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));

        mockMvc.perform(post("/api/v1/zero-waste/brand")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(brand)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.nameBrand", Matchers.is(brand.getNameBrand())));
    }

    @Test
    @WithMockUser(username = "agung", password = "agung", roles = "ADMIN")
    void getBrandById() throws Exception {
        when(brandService.getBrandById("B01")).thenReturn(brand);
        RequestBuilder requestBuilder = get("/api/v1/zero-waste/brand/B01").contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.nameBrand", Matchers.is(brand.getNameBrand())));
    }

    @Test
    void updateBrand() {
    }

    @Test
    void deleteBrand() {
    }

    @Test
    void getAllBrand() {
    }
}