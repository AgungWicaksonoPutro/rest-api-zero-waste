package com.enigma.zerowaste.specification;

import com.enigma.zerowaste.dto.ProductSearchDTO;
import com.enigma.zerowaste.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {
    public static Specification<Product> getSpecification(ProductSearchDTO productSearchDTO){
        return new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!(productSearchDTO.getSearchProductName() == null)){
                    Predicate productNamePredicate = criteriaBuilder.like(criteriaBuilder.upper(root.get("productName")), "%"+productSearchDTO.getSearchProductName().toUpperCase()+"%");
                    predicates.add(productNamePredicate);
                }
                if (!(productSearchDTO.getSearchProductPrice() == null)){
                    Predicate productPricePredicate = criteriaBuilder.equal(root.get("productPrice"), productSearchDTO.getSearchProductPrice());
                    predicates.add(productPricePredicate);
                }
                if (!(productSearchDTO.getSearhProductStock() == null)){
                    Predicate productStockPredicate = criteriaBuilder.equal(root.get("stock"), productSearchDTO.getSearhProductStock());
                    predicates.add(productStockPredicate);
                }
                Predicate[] arrayPredicates = predicates.toArray(new Predicate[predicates.size()]);
                return criteriaBuilder.and(arrayPredicates);
            }
        };
    }
}
