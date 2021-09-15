package com.enigma.zerowaste.service.impl;

import com.enigma.zerowaste.dto.BrandSearchDTO;
import com.enigma.zerowaste.entity.Address;
import com.enigma.zerowaste.entity.Brand;
import com.enigma.zerowaste.exception.DataNotFoundException;
import com.enigma.zerowaste.repository.BrandRepository;
import com.enigma.zerowaste.specification.BrandSpesification;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BrandServiceImplTest {

    @Mock
    private BrandRepository brandRepository;

    @Autowired
    @InjectMocks
    private BrandServiceImpl brandService;
    private Brand brand1;
    private Brand brand2;
    List<Brand> brandList;

    @BeforeEach
    void setUp() {
        brandList = new ArrayList<>();
        brand1 = new Brand("B01", "Sunslick", "Product kebersihan Rambut");
        brand2 = new Brand("B02", "FrisianFlag", "Product susu");
        brandList.add(brand1);
        brandList.add(brand2);
    }

    @Test
    void saveBrand(){
       when(brandRepository.save(any())).thenReturn(brand1);
       brandService.saveBrand(brand1);
       verify(brandRepository, times(1)).save(any());
    }

    @Test
    void getBrandById() {
        when(brandRepository.findById("B01")).thenReturn(Optional.ofNullable(brand1));
        assertThat(brandService.getBrandById(brand1.getId())).isEqualTo(brand1);
    }

    @Test
    void updateBrand() {
        brandRepository.save(brand1);
        Brand newBrand = new Brand("B01", "HAhahah", "hahahah");
        Optional<Brand> optional = Optional.of(brand1);

        when(brandRepository.findById("B01")).thenReturn(optional);
        when(brandRepository.save(any(Brand.class))).thenReturn(newBrand);

        Brand updateBrand = brandService.updateBrand("B01", newBrand);

        assertEquals(updateBrand.getDescription(), newBrand.getDescription());
        assertEquals(updateBrand.getNameBrand(), newBrand.getNameBrand());
    }


    @Test
    void deleteBrand() {
        brandRepository.save(brand1);
        brandService.deleteBrand(brand1.getId());
        assertEquals(0, brandRepository.findAll().size());
    }

    @Test
    void getAllBrand() {
        brandRepository.save(brand1);
        Pageable pageable = PageRequest.of(1, 1);
        BrandSearchDTO brandSearchDTO = new BrandSearchDTO("Sunslick");
        Specification<Brand> brandSpecification = BrandSpesification.getSpesification(brandSearchDTO);
        Page<Brand> brands = brandService.getAllBrand(pageable, brandSearchDTO);
        when(brandRepository.findAll(brandSpecification, pageable)).thenReturn(brands);
        assertEquals(brands, brandRepository.findAll(brandSpecification, pageable));
    }

    @AfterEach
    void tearDown() {
        brand1 = brand2 = null;
        brandList = null;
    }
}