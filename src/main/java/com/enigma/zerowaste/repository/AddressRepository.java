package com.enigma.zerowaste.repository;

import com.enigma.zerowaste.entity.Address;
import com.enigma.zerowaste.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface AddressRepository extends JpaRepository<Address, String> {
    Page<Address>findAll(Specification<Address> addressSpecification, Pageable pageable);
}
