package com.enigma.zerowaste.service.impl;

import com.enigma.zerowaste.dto.OrderItemSearchDTO;
import com.enigma.zerowaste.entity.Order;
import com.enigma.zerowaste.entity.OrderItem;
import com.enigma.zerowaste.entity.Product;
import com.enigma.zerowaste.repository.OrderItemRepository;
import com.enigma.zerowaste.repository.ProductRepository;
import com.enigma.zerowaste.specification.OrderItemSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderItemServiceImplTest {

    @InjectMocks
    OrderItemServiceImpl service;

    @Mock
    OrderItemRepository repository;

    @Autowired
    MockMvc mockMvc;

    private OrderItemSearchDTO orderItemSearchDTO = new OrderItemSearchDTO();
    private OrderItem orderItem;
    private Order order;
    private Product product;

    @BeforeEach
    void setup() {
        orderItem = new OrderItem("oritm01", new Integer(30), new Float(30000), order, product);
    }

    @Test
    void addOrderedProducts() {
        repository.save(orderItem);
        List<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(orderItem);

        when(repository.findAll()).thenReturn(orderItemList);
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void getOrder() {
        repository.save(orderItem);
        orderItemSearchDTO.setSearchAmount(30);
        orderItemSearchDTO.setSearchPrice(new Float(30000));

        Specification<OrderItem> orderItemSpecification = OrderItemSpecification.getSpecification(orderItemSearchDTO);
        List<OrderItem> orderItemList = repository.findAll(orderItemSpecification);

        when(repository.findAll(orderItemSpecification)).thenReturn(orderItemList);
        assertEquals(repository.findAll(orderItemSpecification).size(), service.getOrder(orderItemSearchDTO).size());
    }
}
