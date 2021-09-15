package com.enigma.zerowaste.repository;

import com.enigma.zerowaste.entity.Product;
import com.enigma.zerowaste.entity.QuantityType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuantityTypeRepository extends JpaRepository <QuantityType, String> {
    Page<QuantityType> findAll(Specification<QuantityType> quantityTypeSpecification, Pageable pageable);
}
