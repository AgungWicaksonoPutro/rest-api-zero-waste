package com.enigma.zerowaste.service;

import com.enigma.zerowaste.dto.ProductSearchDTO;
import com.enigma.zerowaste.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    public Product saveProduct(Product product);
    public Product updateProduct(String id,Product product);
    public List<Product> getAllProduct();
    public Product getProductById(String id);
    public void deleteProduct(String id);
    public Page<Product> getPerPage(Pageable pageable, ProductSearchDTO productSearchDTO);
}
