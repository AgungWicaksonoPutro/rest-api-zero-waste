package com.enigma.zerowaste.repository;

import com.enigma.zerowaste.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    Page<Customer> findAll(Specification<Customer> customerSpecification, Pageable pageable);

    Optional<Customer> findByUserName(String username);
    Boolean existsByUserName(String username);
    Boolean existsByEmail(String email);

}
