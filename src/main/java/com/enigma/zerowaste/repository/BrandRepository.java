package com.enigma.zerowaste.repository;

import com.enigma.zerowaste.entity.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface BrandRepository extends JpaRepository<Brand, String> {
    Page<Brand> findAll(Specification<Brand> brandSpecification, Pageable pageable);
}
