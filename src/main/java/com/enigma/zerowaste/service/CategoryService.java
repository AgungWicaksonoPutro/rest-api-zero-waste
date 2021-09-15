package com.enigma.zerowaste.service;

import com.enigma.zerowaste.dto.CategorySearchDTO;
import com.enigma.zerowaste.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    public Category saveCategory(Category category);
    public Category getCategoryById(String id);
    public Category updateProduct(String id, Category category);
    public void deleteProduct(String id);
    public Page<Category> getAllCategory(Pageable pageable, CategorySearchDTO categorySearchDTO);
}
