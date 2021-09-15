package com.enigma.zerowaste.repository;

import com.enigma.zerowaste.entity.Customer;
import com.enigma.zerowaste.entity.ShoppingBag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingBagRepository extends JpaRepository<ShoppingBag, String> {
    List<ShoppingBag> findAllByCustomerOrderByCreatedDateDesc(Customer customer);
    List<ShoppingBag> deleteByCustomer(Customer customer);
}
