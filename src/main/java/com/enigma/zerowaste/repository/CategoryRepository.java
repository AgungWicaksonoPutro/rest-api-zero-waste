package com.enigma.zerowaste.repository;

import com.enigma.zerowaste.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    Page<Category> findAll(Specification<Category> categorySpecification, Pageable pageable);
}
