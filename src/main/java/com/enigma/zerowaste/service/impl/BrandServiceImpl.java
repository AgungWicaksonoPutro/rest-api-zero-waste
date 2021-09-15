package com.enigma.zerowaste.service.impl;

import com.enigma.zerowaste.dto.BrandSearchDTO;
import com.enigma.zerowaste.entity.Brand;
import com.enigma.zerowaste.repository.BrandRepository;
import com.enigma.zerowaste.service.BrandService;
import com.enigma.zerowaste.specification.BrandSpesification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    BrandRepository brandRepository;

    @Override
    public Brand saveBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public Brand getBrandById(String id) {
        return brandRepository.findById(id).get();
    }

    @Override
    public Brand updateBrand(String id, Brand brand) {
        Brand brand1 = getBrandById(id);
        brand1.setNameBrand(brand.getNameBrand());
        brand1.setDescription(brand.getDescription());
        return brandRepository.save(brand1);
    }

    @Override
    public Brand deleteBrand(String id) {
        Brand brand = null;
        Optional<Brand> brand1 = brandRepository.findById(id);
        if (brand1.isPresent()){
            brand = brandRepository.findById(id).get();
            brandRepository.deleteById(id);
        }
        return brand;
    }

    @Override
    public Page<Brand> getAllBrand(Pageable pageable, BrandSearchDTO brandSearchDTO) {
        Specification<Brand> brandSpecification = BrandSpesification.getSpesification(brandSearchDTO);
        return brandRepository.findAll(brandSpecification, pageable);
    }
}
