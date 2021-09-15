package com.enigma.zerowaste.controller;

import com.enigma.zerowaste.service.ShipperService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(ShipperController.class)
class ShipperControllerTest {

    @MockBean
    ShipperService service;



    @Test
    void createShipper() {
    }

    @Test
    void getShipperById() {
    }

    @Test
    void updateShipper() {
    }

    @Test
    void deleteShipperById() {
    }

    @Test
    void getProductPerPage() {
    }
}