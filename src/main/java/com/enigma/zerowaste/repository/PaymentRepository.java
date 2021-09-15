package com.enigma.zerowaste.repository;

import com.enigma.zerowaste.entity.Payment;
import com.enigma.zerowaste.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
    Page<Payment> findAll(Specification<Payment> paymentSpecification, Pageable pageable);
}
