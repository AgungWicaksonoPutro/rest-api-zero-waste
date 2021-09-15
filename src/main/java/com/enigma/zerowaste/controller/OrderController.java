package com.enigma.zerowaste.controller;

import com.enigma.zerowaste.constant.ApiUrlConstant;
import com.enigma.zerowaste.constant.ResponseMessage;
import com.enigma.zerowaste.dto.OrderItemSearchDTO;
import com.enigma.zerowaste.dto.order.OrderDTO;

import com.enigma.zerowaste.dto.order.OrderReceivedDTO;
import com.enigma.zerowaste.dto.order.OrderShippedDTO;

import com.enigma.zerowaste.entity.Order;

import com.enigma.zerowaste.service.OrderService;
import com.enigma.zerowaste.service.impl.ReportService;
import com.enigma.zerowaste.utils.Response;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.jasperreports.engine.JRException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping(ApiUrlConstant.ORDER)
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    ReportService reportService;

    public static Response<Order> response = new Response();
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    public ResponseEntity<Response<Order>> createOrder(@RequestBody OrderDTO order) throws NoSuchAlgorithmException {
        String message = String.format(ResponseMessage.DATA_INSERTED, "Order");
        response.setMessage(message);
        response.setData(orderService.saveOrder(order));
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    @GetMapping("/{id}")
    public ResponseEntity<Response<Order>> getOrderById(@PathVariable String id) {
        String message = String.format(ResponseMessage.GET_DATA, "Order", id);
        response.setMessage(message);
        response.setData(orderService.getOrderById(id));
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/shipped")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    public ResponseEntity<Response<Order>> updateOrderShipped(@RequestBody OrderShippedDTO shippedDTO) {
        String message = String.format("Order %s, %s", shippedDTO.getIdOrder(),ResponseMessage.RESPONSE_ORDER_SHIPPED);
        response.setMessage(message);
        response.setData(orderService.orderShipped(shippedDTO));

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/received")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    public ResponseEntity<Response<Order>> updateOrderReceived(@RequestBody OrderReceivedDTO orderReceivedDTO) {
        String message = String.format("Order %s, %s",orderReceivedDTO.getIdOrder(),ResponseMessage.RESPONSE_ORDER_RECEIVED);
        response.setMessage(message);
        response.setData(orderService.orderRecived(orderReceivedDTO));
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/report")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    public String getOrder(@RequestParam(name = "price", required = false) Float price,
                           @RequestParam(name = "amount", required = false) Integer amount) throws JRException, FileNotFoundException {
        OrderItemSearchDTO orderItemSearchDTO = new OrderItemSearchDTO();
        orderItemSearchDTO.setSearchPrice(price);
        orderItemSearchDTO.setSearchAmount(amount);
        return reportService.exportReport(orderItemSearchDTO);
    }
}
