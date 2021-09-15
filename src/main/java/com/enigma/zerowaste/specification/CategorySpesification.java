package com.enigma.zerowaste.specification;

import com.enigma.zerowaste.dto.CategorySearchDTO;
import com.enigma.zerowaste.entity.Category;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class CategorySpesification {
    public static Specification<Category> getSpesification(CategorySearchDTO categorySearchDTO){
      return new Specification<Category>() {
          @Override
          public Predicate toPredicate(Root<Category> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
              List<Predicate> predicates = new ArrayList<>();
              if (categorySearchDTO.getSerachCategoryName() != null){
                  Predicate categoryName = criteriaBuilder.like(criteriaBuilder.lower(root.get("nameCategory")), "%"+ categorySearchDTO.getSerachCategoryName().toLowerCase() + "%");
                  predicates.add(categoryName);
              }
              Predicate[] arrayPredicates = predicates.toArray(predicates.toArray(new Predicate[predicates.size()]));
              return criteriaBuilder.and(arrayPredicates);
          }
      };
    }
}
