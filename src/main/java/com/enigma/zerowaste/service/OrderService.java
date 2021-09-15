package com.enigma.zerowaste.service;

import com.enigma.zerowaste.dto.order.OrderDTO;
import com.enigma.zerowaste.dto.order.OrderReceivedDTO;
import com.enigma.zerowaste.dto.order.OrderShippedDTO;
import com.enigma.zerowaste.entity.Order;

import java.security.NoSuchAlgorithmException;

public interface OrderService {
    public Order saveOrder(OrderDTO orderDTO) throws NoSuchAlgorithmException;
    public Order getOrderById(String id);
    public void updateOrder(Order order);
    public Order orderShipped(OrderShippedDTO orderShippedDTO);
    public Order orderRecived(OrderReceivedDTO orderReceivedDTO);
}
