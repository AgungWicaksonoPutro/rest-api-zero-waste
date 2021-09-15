package com.enigma.zerowaste.service.impl;

import com.enigma.zerowaste.constant.ResponseMessage;
import com.enigma.zerowaste.dto.ProductSearchDTO;
import com.enigma.zerowaste.entity.Product;
import com.enigma.zerowaste.repository.ProductRepository;
import com.enigma.zerowaste.service.ProductService;
import com.enigma.zerowaste.specification.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository repository;

    public String productValidation(String id) {
        String message="";
        if (!(repository.existsById(id))) {
            message = String.format(ResponseMessage.NOT_FOUND_MESSAGE, "product", id);
        }
        return message;
    }

    @Override
    public Product saveProduct(Product product) {
        return repository.save(product);
    }

    @Override
    public Product updateProduct(String id, Product product) {
        Product productUpdate = getProductById(id);
        productUpdate.setStock(product.getStock());
        return repository.save(productUpdate);
    }

    @Override
    public List<Product> getAllProduct() {
        return repository.findAll();
    }

    @Override
    public Product getProductById(String id) {
        productValidation(id);
        return repository.findById(id).get();
    }

    @Override
    public void deleteProduct(String id) {
        productValidation(id);
        repository.deleteById(id);
    }

    @Override
    public Page<Product> getPerPage(Pageable pageable, ProductSearchDTO productSearchDTO) {
        Specification<Product> productSpecification = ProductSpecification.getSpecification(productSearchDTO);
        return repository.findAll(productSpecification, pageable);
    }
}
