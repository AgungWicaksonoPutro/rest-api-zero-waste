package com.enigma.zerowaste.service.impl;

import com.enigma.zerowaste.dto.OrderItemSearchDTO;
import com.enigma.zerowaste.entity.OrderItem;
import com.enigma.zerowaste.repository.OrderItemRepository;
import com.enigma.zerowaste.service.OrderItemService;
import com.enigma.zerowaste.specification.OrderItemSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public void addOrderedProducts(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }

    @Override
    public List<OrderItem> getOrder(OrderItemSearchDTO orderItemSearchDTO) {
        Specification<OrderItem> orderItemSpecification = OrderItemSpecification.getSpecification(orderItemSearchDTO);
        return orderItemRepository.findAll(orderItemSpecification);
    }
}
