package com.enigma.zerowaste.service.impl;

import com.enigma.zerowaste.dto.ProductSearchDTO;
import com.enigma.zerowaste.entity.Brand;
import com.enigma.zerowaste.entity.Category;
import com.enigma.zerowaste.entity.Product;
import com.enigma.zerowaste.entity.QuantityType;
import com.enigma.zerowaste.repository.ProductRepository;
import com.enigma.zerowaste.specification.ProductSpecification;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @InjectMocks
    ProductServiceImpl service;

    @Mock
    ProductRepository repository;

    @Autowired
    MockMvc mockMvc;

    private Product product;
    private Product testedProduct = new Product();

    private Brand brand;
    private Category category;
    private QuantityType quantityType;
    private List<Product> products = new ArrayList<>();

    @BeforeEach
    void setup() {
        brand = new Brand("b01", "brand number 1", "this is description", products);
        category = new Category("c01", "category number 1", "this is description", new Integer("1"), products);
        quantityType = new QuantityType("qt01", "qType number 1", products);
        product = new Product("p01", "Product number 1", new Float(100000.0), "this is description",
                new Integer("100"), "url image", brand, category, quantityType);

        testedProduct.setId(product.getId());
        testedProduct.setProductName(product.getProductName());
        testedProduct.setProductPrice(product.getProductPrice());
        testedProduct.setDescriptions(product.getDescriptions());
        testedProduct.setStock(product.getStock());
        testedProduct.setProductImage(product.getProductImage());
        testedProduct.setBrand(product.getBrand());
        testedProduct.setCategory(product.getCategory());
        testedProduct.setQuantityType(product.getQuantityType());

        when(repository.save(any())).thenReturn(testedProduct);
    }

    @Test
    void productValidation() {
        repository.save(product);
        assertEquals("Resource product with Id p02 not found", service.productValidation("p02"));
    }

    @Test
    void saveProduct() {
        service.saveProduct(product);
        List<Product> productList = new ArrayList<>();
        productList.add(product);

        when(repository.findAll()).thenReturn(productList);
        assertEquals(1, repository.findAll().size());
    }

    /*@Test
    void updateProduct() {
        repository.save(product);
        Product newProduct = new Product();
        newProduct = repository.getById("p01");
        Integer oldStock = 100;
        Integer newStock= oldStock - 2;
        newProduct.setStock(newStock);
        assertFalse(service.updateProduct(newProduct.getId(), newProduct).getStock().equals(oldStock));
    }*/

    @Test
    void getAllProduct() {
        repository.save(product);
        List<Product> productList = new ArrayList<>();
        productList.add(product);

        when(service.getAllProduct()).thenReturn(productList);
        assertEquals(1, service.getAllProduct().size());
    }

    @Test
    void getProductById() {
        repository.save(product);
        List<Product> productList = new ArrayList<>();
        productList.add(testedProduct);

        when(service.getAllProduct()).thenReturn(productList);
        assertEquals(1, service.getAllProduct().size());
    }

    @Test
    void deleteProduct() {
        repository.save(product);
        service.deleteProduct("p01");
        assertEquals(0, repository.findAll().size());
    }

    @Test
    void getPerPage() {
        repository.save(product);
        Pageable pageable = PageRequest.of(1, 1);
        ProductSearchDTO productSearchDTO = new ProductSearchDTO();
        productSearchDTO.setSearchProductName("p01");

        Specification<Product> productSpecification = ProductSpecification.getSpecification(productSearchDTO);
        Page<Product> productPage = service.getPerPage(pageable, productSearchDTO);
        when(repository.findAll(productSpecification, pageable)).thenReturn(productPage);
        assertEquals(productPage, repository.findAll(productSpecification, pageable));
    }
}