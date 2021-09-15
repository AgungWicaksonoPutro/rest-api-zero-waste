package com.enigma.zerowaste.service;

import com.enigma.zerowaste.dto.BrandSearchDTO;
import com.enigma.zerowaste.entity.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BrandService {
    public Brand saveBrand(Brand brand);
    public Brand getBrandById(String id);
    public Brand updateBrand(String id, Brand brand);
    public Brand deleteBrand(String id);
    public Page<Brand> getAllBrand(Pageable pageable, BrandSearchDTO brandSearchDTO);
}
