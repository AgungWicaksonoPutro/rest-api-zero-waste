package com.enigma.zerowaste.specification;

import com.enigma.zerowaste.dto.order.OrderSearchDTO;
import com.enigma.zerowaste.entity.Order;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class OrderSpesification {
    public static Specification<Order> getSpesification(OrderSearchDTO orderSearchDTO){
        return new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!(orderSearchDTO.getSearchOrderId() == null)){
                    Predicate orderIdPredicate = criteriaBuilder.like(criteriaBuilder.upper(root.get("id")),"%"+orderSearchDTO.getSearchOrderId().toUpperCase()+"%");
                    predicates.add(orderIdPredicate);
                }
//                if (!(orderSearchDTO.getSearchPaymentDate()==null)){
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
//                    String modifiedDateFormated = sdf.format(orderSearchDTO.getSearchPaymentDate());
//
//                    Predicate createDatePredicate = criteriaBuilder.equal(criteriaBuilder.function("TO_CHAR",String.class,root.get("paymentDate"),criteriaBuilder.literal("yyyy-MM-dd")),modifiedDateFormated);
//                    predicates.add(createDatePredicate);
//                }
                Predicate[] arrayPredicate = predicates.toArray(new Predicate[predicates.size()]);
                return criteriaBuilder.and(arrayPredicate);
            }
        };
    }
}
