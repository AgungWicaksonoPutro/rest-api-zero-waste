package com.enigma.zerowaste.service.impl;

import com.enigma.zerowaste.dto.ProductSearchDTO;
import com.enigma.zerowaste.dto.QuantityTypeSearchDTO;
import com.enigma.zerowaste.entity.Address;
import com.enigma.zerowaste.entity.Product;
import com.enigma.zerowaste.entity.QuantityType;
import com.enigma.zerowaste.repository.ProductRepository;
import com.enigma.zerowaste.repository.QuantityTypeRepository;
import com.enigma.zerowaste.specification.ProductSpecification;
import com.enigma.zerowaste.specification.QuantityTypeSpecification;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuantityTypeServiceImplTest {

    @InjectMocks
    QuantityTypeServiceImpl service;

    @Mock
    QuantityTypeRepository repository;

    @Autowired
    MockMvc mockMvc;

    private QuantityType qType = new QuantityType();
    private List<Product> productList = new ArrayList<>();

    @BeforeEach
    void setup(){
        qType = new QuantityType("qt01", "pcs", "quantity type number 1", productList);
    }

    @Test
    void qTypeValidation() {
        repository.save(qType);
        assertEquals("Resource quantity type with Id qt02 not found", service.qTypeValidation("qt02"));
    }

    @Test
    void saveQType() {
        service.saveQType(qType);
        List<QuantityType> qTypeList = new ArrayList<>();
        qTypeList.add(qType);

        when(repository.findAll()).thenReturn(qTypeList);
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void getAllQType() {
        repository.save(qType);
        List<QuantityType> qTypeList = new ArrayList<>();
        qTypeList.add(qType);

        when(service.getAllQType()).thenReturn(qTypeList);
        assertEquals(1, service.getAllQType().size());
    }

    @Test
    void getQTypeById() {
        repository.save(qType);
        QuantityType oldQType = repository.getById(qType.getId());
        List<QuantityType> qTypeList = new ArrayList<>();
        qTypeList.add(oldQType);

        when(service.getAllQType()).thenReturn(qTypeList);
        assertEquals(1, service.getAllQType().size());
    }

    @Test
    void deleteQuantityType() {
        repository.save(qType);
        service.deleteQuantityType("qt01");
        assertEquals(0, repository.findAll().size());
    }

    @Test
    void getPerPage() {
        repository.save(qType);
        Pageable pageable = PageRequest.of(1, 1);
        QuantityTypeSearchDTO quantityTypeSearchDTO = new QuantityTypeSearchDTO();
        quantityTypeSearchDTO.setSearchQTypeName("qt01");

        Specification<QuantityType> qTypeSpecification = QuantityTypeSpecification.getSpecification(quantityTypeSearchDTO);
        Page<QuantityType> qTypePage = service.getPerPage(pageable, quantityTypeSearchDTO);
        when(repository.findAll(qTypeSpecification, pageable)).thenReturn(qTypePage);
        assertEquals(qTypePage, repository.findAll(qTypeSpecification, pageable));
    }
}