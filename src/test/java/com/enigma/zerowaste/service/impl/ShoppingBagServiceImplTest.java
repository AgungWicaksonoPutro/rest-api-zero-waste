package com.enigma.zerowaste.service.impl;

import com.enigma.zerowaste.dto.shoppingbag.AddToShoppingBagDTO;
import com.enigma.zerowaste.dto.shoppingbag.ShoppingBagDTO;
import com.enigma.zerowaste.dto.shoppingbag.ShoppingBagItemDTO;
import com.enigma.zerowaste.entity.Customer;
import com.enigma.zerowaste.entity.Product;
import com.enigma.zerowaste.entity.Shipper;
import com.enigma.zerowaste.entity.ShoppingBag;
import com.enigma.zerowaste.repository.CustomerRepository;
import com.enigma.zerowaste.repository.ProductRepository;
import com.enigma.zerowaste.repository.ShoppingBagRepository;
import com.enigma.zerowaste.security.services.CustomerDetailsImpl;
import com.enigma.zerowaste.service.CustomerService;
import com.enigma.zerowaste.service.ProductService;
import com.enigma.zerowaste.service.ShoppingBagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class ShoppingBagServiceImplTest {

    @MockBean
    ShoppingBagRepository shoppingBagRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    ProductService productService;

    @Autowired
    ShoppingBagService shoppingBagService;

    @MockBean
    CustomerRepository customerRepository;

    @MockBean
    ProductRepository productRepository;



    private AddToShoppingBagDTO addToShoppingBagDTO;
    private Customer customer;
    private Product product;
    private ShoppingBag shoppingBag;
    private ShoppingBag output;
    private Authentication authentication;
    private SecurityContext securityContext;
    private SecurityContextHolder securityContextHolder;


    @BeforeEach
    void setup(){
        authentication = Mockito.mock(Authentication.class);
        securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        securityContextHolder.setContext(securityContext);
        shoppingBag = new ShoppingBag("idShoppingBag",customer,product,5);
        output = new ShoppingBag();
        output.setId(shoppingBag.getId());
        output.setCustomer(shoppingBag.getCustomer());
        output.setProduct(shoppingBag.getProduct());
        output.setAmount(shoppingBag.getAmount());
        when(shoppingBagRepository.save(any())).thenReturn(output);
    }

    @Test
    void addToShoppingBag() {
        customer = new Customer();
        customer.setId("idCustomer");
        product = new Product();
        product.setId("idProduct");
        product.setStock(10);
        when(productRepository.findById(any())).thenReturn(Optional.ofNullable(product));

        when(customerRepository.findById(customer.getId())).thenReturn(Optional.ofNullable(customer));
        CustomerDetailsImpl cdi = new CustomerDetailsImpl();
        cdi.setId(customer.getId());
        Mockito.when(securityContext.getAuthentication().getPrincipal()).thenReturn(cdi);

        addToShoppingBagDTO = new AddToShoppingBagDTO(product.getId(),5);
        ShoppingBag newShoppingBag = shoppingBagService.addToShoppingBag(addToShoppingBagDTO);
        assertEquals(output,newShoppingBag);

    }

    @Test
    void updateShoppingBag() {
        product = new Product();
        product.setId("idProduct");
        product.setStock(10);
        shoppingBag.setProduct(product);
        addToShoppingBagDTO = new AddToShoppingBagDTO(product.getId(),5);
        when(shoppingBagRepository.findById(any())).thenReturn(Optional.ofNullable(shoppingBag));
        when(productRepository.findById(any())).thenReturn(Optional.ofNullable(product));

        ShoppingBag updateShoppingBag = shoppingBagService.updateShoppingBag(shoppingBag.getId(),addToShoppingBagDTO);

        assertEquals(output,updateShoppingBag);
    }

    @Test
    void deleteShoppingBag() {
        ShoppingBag shoppingBag1 = new ShoppingBag(customer,product,9);
        shoppingBagRepository.save(shoppingBag1);
        when(shoppingBagRepository.existsById(any())).thenReturn(true);
        shoppingBagService.deleteShoppingBag(shoppingBag.getId());
        assertEquals(0,shoppingBagRepository.findAll().size());

    }

    @Test
    void getAllShoppingBag() {
        customer = new Customer();
        customer.setId("cus_qwe123");
        product = new Product();
        product.setId("123asd2qw");
        product.setStock(10);
        product.setProductPrice(Float.valueOf(10000));
        when(productRepository.findById(any())).thenReturn(Optional.ofNullable(product));


        //handling line 70 -72
        CustomerDetailsImpl cdi = new CustomerDetailsImpl();
        cdi.setId(customer.getId());
        Mockito.when(securityContext.getAuthentication().getPrincipal()).thenReturn(cdi);
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.ofNullable(customer));

        List<ShoppingBag> shoppingBagList = new ArrayList<>();
        shoppingBag = new ShoppingBag(customer,product,5);
        shoppingBagList.add(shoppingBag);
        when(shoppingBagRepository.findAllByCustomerOrderByCreatedDateDesc(customer)).thenReturn(shoppingBagList);

        ShoppingBagDTO shoppingBagDtoExpected = new ShoppingBagDTO();
        List<ShoppingBagItemDTO> shoppingBagItem = new ArrayList<>();
        shoppingBagItem.add(new ShoppingBagItemDTO(shoppingBag));
        shoppingBagDtoExpected.setShoppingBagItems(shoppingBagItem);
        shoppingBagDtoExpected.setTotalPrice(Double.valueOf(50000));

        ShoppingBagDTO shoppingBagDTO = shoppingBagService.getAllShoppingBag();
        assertEquals(shoppingBagDtoExpected.toString(),shoppingBagDTO.toString());

    }

    @Test
    void getShoppingBag() {
        when(shoppingBagRepository.findById(any())).thenReturn(Optional.ofNullable(shoppingBag));

        ShoppingBag shoppingBagId = shoppingBagService.getShoppingBag(shoppingBag.getId());

        assertEquals(shoppingBagId,shoppingBag);
    }

    @Test
    void getDTOShoppingBag() {
    }

    @Test
    void cekStock() {
        product = new Product();
        product.setId("123asd2qw");
        product.setStock(0);
        product.setProductPrice(Float.valueOf(10000));
        when(productRepository.findById(any())).thenReturn(Optional.ofNullable(product));


    }

    @Test
    void transactionalStockForUpdate() {
    }
}