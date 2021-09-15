package com.enigma.zerowaste.service.impl;

import com.enigma.zerowaste.dto.CategorySearchDTO;
import com.enigma.zerowaste.entity.Category;
import com.enigma.zerowaste.repository.CategoryRepository;
import com.enigma.zerowaste.service.CategoryService;
import com.enigma.zerowaste.specification.CategorySpesification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category getCategoryById(String id) {
        return categoryRepository.findById(id).get();
    }

    @Override
    public Category updateProduct(String id, Category category) {
        Category category1 = getCategoryById(id);
        category1.setNameCategory(category.getNameCategory());
        category1.setDescription(category.getDescription());
        return categoryRepository.save(category1);
    }

    @Override
    public void deleteProduct(String id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Page<Category> getAllCategory(Pageable pageable, CategorySearchDTO categorySearchDTO) {
        Specification<Category> categorySpecification = CategorySpesification.getSpesification(categorySearchDTO);
        return categoryRepository.findAll(categorySpecification, pageable);
    }
}
