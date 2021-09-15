package com.enigma.zerowaste.specification;

import com.enigma.zerowaste.dto.BrandSearchDTO;
import com.enigma.zerowaste.entity.Brand;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class BrandSpesification {
    public static Specification<Brand> getSpesification(BrandSearchDTO brandSearchDTO){
        return new Specification<Brand>() {
            @Override
            public Predicate toPredicate(Root<Brand> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(brandSearchDTO.getSearchBrandName() != null){
                    Predicate brandName = criteriaBuilder.like(criteriaBuilder.lower(root.get("nameBrand")), "%" + brandSearchDTO.getSearchBrandName().toLowerCase() + "%");
                    predicates.add(brandName);
                }
                Predicate[] arrayPredicates = predicates.toArray(predicates.toArray(new Predicate[predicates.size()]));
                return criteriaBuilder.and(arrayPredicates);
            }
        };
    }
}
