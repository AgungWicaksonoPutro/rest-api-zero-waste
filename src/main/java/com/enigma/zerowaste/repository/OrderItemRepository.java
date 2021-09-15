package com.enigma.zerowaste.repository;

import com.enigma.zerowaste.entity.Order;
import com.enigma.zerowaste.entity.OrderItem;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, String> {
    List<OrderItem> findAll(Specification<OrderItem> orderItemSpecification);
}
