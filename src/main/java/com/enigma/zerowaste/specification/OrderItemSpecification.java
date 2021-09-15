package com.enigma.zerowaste.specification;

import com.enigma.zerowaste.dto.OrderItemSearchDTO;
import com.enigma.zerowaste.dto.ProductSearchDTO;
import com.enigma.zerowaste.entity.OrderItem;
import com.enigma.zerowaste.entity.Product;
import com.enigma.zerowaste.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class OrderItemSpecification {

    public static Specification<OrderItem> getSpecification(OrderItemSearchDTO orderItemSearchDTO){
        return new Specification<OrderItem>() {
            ProductSearchDTO productSearchDTO = new ProductSearchDTO();
            Product product = new Product();
            ProductService service;
            @Override
            public Predicate toPredicate(Root<OrderItem> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!(orderItemSearchDTO.getSearchAmount() == null)) {
                    Predicate amountPredicate = criteriaBuilder.equal(root.get("amount"), orderItemSearchDTO.getSearchAmount());
                    predicates.add(amountPredicate);
                }
                if (!(orderItemSearchDTO.getSearchPrice() == null)) {
                    Predicate pricePredicate = criteriaBuilder.equal(root.get("price"), orderItemSearchDTO.getSearchPrice());
                    predicates.add(pricePredicate);
                }
                Predicate[] arrayPredicate = predicates.toArray(new Predicate[predicates.size()]);
                return criteriaBuilder.and(arrayPredicate);
            }
        };
    }
}
