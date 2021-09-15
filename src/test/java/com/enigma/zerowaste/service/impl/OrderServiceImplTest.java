package com.enigma.zerowaste.service.impl;

import com.enigma.zerowaste.dto.order.OrderDTO;
import com.enigma.zerowaste.entity.Customer;
import com.enigma.zerowaste.entity.Order;
import com.enigma.zerowaste.entity.Shipper;
import com.enigma.zerowaste.repository.CustomerRepository;
import com.enigma.zerowaste.repository.OrderRepository;
import com.enigma.zerowaste.repository.ShipperRepository;
import com.enigma.zerowaste.service.CustomerService;
import com.enigma.zerowaste.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @InjectMocks
    OrderServiceImpl orderService;

    @InjectMocks
    CustomerServiceImpl customerService;

    @Mock
    OrderRepository orderRepository;

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    ShipperServiceImpl shipperService;

    @Mock
    ShipperRepository shipperRepository;

    private Customer customer;
    private Shipper shipper;
    private Order order;
    private OrderDTO orderDTO;

    @BeforeEach
    void setup(){
        customer = new Customer();
        customer.setId("Cus01");
        customer.setCustomerName("Badang");
        customer.setPassword("hgdhdhduf");
        customer.setUserName("badang04");
        customer.setEmail("badang@mail.com");
        customer.setEmailVerfied(false);
        customer.setPhone("08908798786");

        shipper = new Shipper();
        shipper.setId("S01");
        shipper.setName("ADA");
        shipper.setDescription("Ada Ada saja");
        shipper.setPrice(858490.0);
    }

    @Test
    void orderValidation() {
    }

    @Test
    void getCustomer() {
        when(customerRepository.findById("Cus01")).thenReturn(Optional.ofNullable(customer));
        assertThat(customerService.getCustomerById(customer.getId())).isEqualTo(customer);
    }

    @Test
    void getShipper() {
        when(shipperRepository.findById("S01")).thenReturn(Optional.ofNullable(shipper));
        assertThat(shipperService.getShipperById(shipper.getId())).isEqualTo(shipper);
    }

    @Test
    void saveOrder() {
    }

    @Test
    void generateRandomString() {
    }

    @Test
    void getOrderById() {
    }

    @Test
    void updateOrder() {
    }

    @Test
    void orderShipped() {
    }

    @Test
    void orderRecived() {
    }
}