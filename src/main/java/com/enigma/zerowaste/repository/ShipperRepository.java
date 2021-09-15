package com.enigma.zerowaste.repository;

import com.enigma.zerowaste.entity.Shipper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipperRepository extends JpaRepository<Shipper, String> {
    Page<Shipper> findAll(Specification<Shipper> shipperSpecification, Pageable pageable);
}
