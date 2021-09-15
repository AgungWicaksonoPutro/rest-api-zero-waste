package com.enigma.zerowaste.service.impl;

import com.enigma.zerowaste.dto.ProductSearchDTO;
import com.enigma.zerowaste.dto.ShipperSearchDTO;
import com.enigma.zerowaste.entity.Order;
import com.enigma.zerowaste.entity.Product;
import com.enigma.zerowaste.entity.QuantityType;
import com.enigma.zerowaste.entity.Shipper;
import com.enigma.zerowaste.repository.ProductRepository;
import com.enigma.zerowaste.repository.ShipperRepository;
import com.enigma.zerowaste.specification.ProductSpecification;
import com.enigma.zerowaste.specification.ShipperSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShipperServiceImplTest {

    @InjectMocks
    ShipperServiceImpl service;

    @Mock
    ShipperRepository repository;

    @Autowired
    MockMvc mockMvc;

    private Shipper shipper;
    private List<Order> orderList = new ArrayList<>();

    @BeforeEach
    void setup() {
        shipper = new Shipper("shp01", "YES", "Yakin Esok Sampai", 20000.0, orderList);
    }

    @Test
    void shipperValidation() {
        repository.save(shipper);
        assertEquals("Resource shipper with Id shp02 not found", service.shipperValidation("shp02"));
    }

    @Test
    void saveShipper() {
        service.saveShipper(shipper);
        List<Shipper> shipperList = new ArrayList<>();
        shipperList.add(shipper);

        when(repository.findAll()).thenReturn(shipperList);
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void getAllShipper() {
        repository.save(shipper);
        List<Shipper> shipperList = new ArrayList<>();
        shipperList.add(shipper);

        when(service.getAllShipper()).thenReturn(shipperList);
        assertEquals(1, service.getAllShipper().size());
    }

    @Test
    void getShipperById() {
        repository.save(shipper);
        Shipper oldShipper = repository.getById(shipper.getId());
        List<Shipper> shipperList = new ArrayList<>();
        shipperList.add(oldShipper);

        when(service.getAllShipper()).thenReturn(shipperList);
        assertEquals(1, service.getAllShipper().size());
    }

    @Test
    void deleteShipper() {
        repository.save(shipper);
        service.deleteShipper("shp01");
        assertEquals(0, repository.findAll().size());
    }

    @Test
    void getPerPage() {
        repository.save(shipper);
        Pageable pageable = PageRequest.of(1, 1);
        ShipperSearchDTO shipperSearchDTO = new ShipperSearchDTO();
        shipperSearchDTO.setSearchShipperName("shp01");

        Specification<Shipper> shipperSpecification = ShipperSpecification.getSpecification(shipperSearchDTO);
        Page<Shipper> shipperPage = service.getPerPage(pageable, shipperSearchDTO);
        when(repository.findAll(shipperSpecification, pageable)).thenReturn(shipperPage);
        assertEquals(shipperPage, repository.findAll(shipperSpecification, pageable));
    }
}
