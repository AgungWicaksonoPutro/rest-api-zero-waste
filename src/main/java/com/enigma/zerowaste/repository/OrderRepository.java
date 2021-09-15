package com.enigma.zerowaste.repository;

import com.enigma.zerowaste.entity.Customer;
import com.enigma.zerowaste.entity.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface OrderRepository extends JpaRepository<Order,String> {
    List<Order> findAllByCustomerOrderByCreatedDateDesc(Customer customer);
}
