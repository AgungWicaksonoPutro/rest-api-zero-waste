package com.enigma.zerowaste.service.impl;

import com.enigma.zerowaste.constant.ResponseMessage;
import com.enigma.zerowaste.dto.order.OrderDTO;
import com.enigma.zerowaste.dto.invoice.InvoiceDTO;
import com.enigma.zerowaste.dto.order.OrderReceivedDTO;
import com.enigma.zerowaste.dto.order.OrderShippedDTO;
import com.enigma.zerowaste.entity.*;
import com.enigma.zerowaste.repository.OrderRepository;
import com.enigma.zerowaste.security.services.CustomerDetailsImpl;
import com.enigma.zerowaste.service.CustomerService;
import com.enigma.zerowaste.service.OrderService;
import com.enigma.zerowaste.service.ShipperService;
import com.enigma.zerowaste.service.ShoppingBagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Transactional
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ShoppingBagService shoppingBagService;

    @Autowired
    ShipperService shipperService;

    @Autowired
    OrderItemServiceImpl orderItemService;

    @Autowired
    CustomerService customerService;

    @Autowired
    PaymentServiceImpl paymentService;

    public void orderValidation(String id) {
        if (orderRepository.existsById(id)) {
            String message = String.format(ResponseMessage.NOT_FOUND_MESSAGE, "Order", id);
        }
    }

    public Customer getCustomer(){
        CustomerDetailsImpl customerDetails = (CustomerDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Customer customer = customerService.getCustomerById(customerDetails.getId());
        return customer;
    }

    public Shipper getShipper(OrderDTO orderDTO){
        Shipper shipper = shipperService.getShipperById(orderDTO.getIdShipper());
        return shipper;
    }

    @Override
    public Order saveOrder(OrderDTO orderDto) throws NoSuchAlgorithmException {
        Shipper shipper = getShipper(orderDto);
        String email = getCustomer().getEmail();
        Double costContainer = 0.0;
        if (getCustomer().getNewMember()){
            getCustomer().setNewMember(false);
            customerService.saveCustomer(getCustomer());
            costContainer = 50000.0;
        }
        Double itemPrice = 0.0;
        for (String id: orderDto.getIdShoppingBags()) {
            ShoppingBag shoppingBag = shoppingBagService.getShoppingBag(id);
            itemPrice += (shoppingBag.getProduct().getProductPrice() * shoppingBag.getAmount());
        }
        Double finalPrice = shipper.getPrice() + itemPrice + costContainer;
        //invoice dto , generate random string xendit minta externall id , final price -> amount, email->email payer, descriptions
        InvoiceDTO invoiceDTO = new InvoiceDTO(generateRandomString(), finalPrice, email, "Zero waste transaction at " + new Date());

        Payment payment = paymentService.makeInvoice(invoiceDTO);
        Order newOrder = new Order(email, finalPrice, "Order Created", shipper, getCustomer(), payment, costContainer);
        Order order = orderRepository.save(newOrder);
        //extrnal id -> update di payment
        payment.setExternalId(order.getId());
        paymentService.savePayment(payment);
        for (String id: orderDto.getIdShoppingBags()) {
            ShoppingBag shoppingBag = shoppingBagService.getShoppingBag(id);
            OrderItem orderItem = new OrderItem(
                    shoppingBag.getAmount(),
                    shoppingBag.getProduct().getProductPrice(),
                    newOrder,
                    shoppingBag.getProduct()
                    );
            orderItemService.addOrderedProducts(orderItem);
            shoppingBagService.deleteShoppingBag(id);
        }
        return order;
    }

    public String generateRandomString() throws NoSuchAlgorithmException {
        MessageDigest instance = MessageDigest.getInstance("MD5");
        byte[] messageDigest = instance.digest(String.valueOf(System.nanoTime()).getBytes());
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < messageDigest.length; i++){
            String hex = Integer.toHexString(0xFF & messageDigest[i]);
            if (hex.length() == 1){
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    @Override
    public Order getOrderById(String id) {
        orderValidation(id);
        return orderRepository.findById(id).get();
    }

    @Override
    public void updateOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    public Order orderShipped(OrderShippedDTO orderShippedDTO) {
        Order order = orderRepository.getById(orderShippedDTO.getIdOrder());
        order.setStatus(ResponseMessage.ORDER_SHIPPED);
        return orderRepository.save(order);
    }

    @Override
    public Order orderRecived(OrderReceivedDTO orderReceivedDTO) {
        Order order = orderRepository.getById(orderReceivedDTO.getIdOrder());
        order.setStatus(ResponseMessage.ORDER_RECEIVED);
        return orderRepository.save(order);
    }


}
