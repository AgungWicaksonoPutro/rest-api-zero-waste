package com.enigma.zerowaste.service;

import com.enigma.zerowaste.dto.OrderItemSearchDTO;
import com.enigma.zerowaste.dto.order.OrderSearchDTO;
import com.enigma.zerowaste.entity.Order;
import com.enigma.zerowaste.entity.OrderItem;

import java.util.List;

public interface OrderItemService {
    public void addOrderedProducts(OrderItem orderItem);
    public List<OrderItem> getOrder(OrderItemSearchDTO orderItemSearchDTO);
}
