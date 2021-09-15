package com.enigma.zerowaste.service.impl;

import com.enigma.zerowaste.dto.BrandSearchDTO;
import com.enigma.zerowaste.dto.CategorySearchDTO;
import com.enigma.zerowaste.entity.Brand;
import com.enigma.zerowaste.entity.Category;
import com.enigma.zerowaste.repository.CategoryRepository;
import com.enigma.zerowaste.specification.BrandSpesification;
import com.enigma.zerowaste.specification.CategorySpesification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Autowired
    @InjectMocks
    private CategoryServiceImpl categoryService;
    private Category category1;
    private Category category2;
    private List<Category> categories;

    @BeforeEach
    void setUp() {
        categories = new ArrayList<>();
        category1 = new Category("C01", "Kebersihan", "Product kebersihan", 1);
        category2 = new Category("C02", "Makanan", "Product makanan", 1);
        categories.add(category1);
        categories.add(category2);
    }

    @AfterEach
    void tearDown() {
        category1 = category2 = null;
        categories = null;
    }

    @Test
    void saveCategory() {
        when(categoryRepository.save(any())).thenReturn(category1);
        categoryService.saveCategory(category1);
        verify(categoryRepository, times(1)).save(any());
    }

    @Test
    void getCategoryById() {
        when(categoryRepository.findById("C01")).thenReturn(Optional.ofNullable(category1));
        assertThat(categoryService.getCategoryById(category1.getId())).isEqualTo(category1);
    }

    @Test
    void updateProduct() {
        categoryService.saveCategory(category1);
        Category newCategory = new Category("C01", "Pembersih Kmar Mandi", "Pembersih", 2);
        Optional<Category> optional = Optional.of(category1);

        when(categoryRepository.findById("C01")).thenReturn(optional);
        when(categoryRepository.save(any(Category.class))).thenReturn(newCategory);

        Category updateCategory = categoryService.updateProduct("C01", newCategory);

        assertEquals(updateCategory.getNameCategory(), newCategory.getNameCategory());
        assertEquals(updateCategory.getIsActive(), newCategory.getIsActive());
    }

    @Test
    void deleteProduct() {
        categoryService.saveCategory(category1);
        categoryService.deleteProduct(category1.getId());
        assertEquals(0, categoryRepository.findAll().size());
    }

    @Test
    void getAllCategory() {
        categoryRepository.save(category1);
        Pageable pageable = PageRequest.of(1,1);
        CategorySearchDTO categorySearchDTO = new CategorySearchDTO("");
        Specification<Category> categorySpecification = CategorySpesification.getSpesification(categorySearchDTO);
        Page<Category> categoryPage = categoryService.getAllCategory(pageable, categorySearchDTO);
        when(categoryRepository.findAll(categorySpecification, pageable)).thenReturn(categoryPage);
        assertEquals(categoryPage, categoryRepository.findAll(categorySpecification, pageable));
    }
}