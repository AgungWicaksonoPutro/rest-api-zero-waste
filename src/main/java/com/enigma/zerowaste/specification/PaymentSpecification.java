package com.enigma.zerowaste.specification;

import com.enigma.zerowaste.dto.PaymentSearchDTO;
import com.enigma.zerowaste.entity.Payment;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class PaymentSpecification {
    public static Specification<Payment> getSpecification(PaymentSearchDTO paymentSearchDTO) {
        return new Specification<Payment>() {
            @Override
            public Predicate toPredicate(Root<Payment> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!(paymentSearchDTO.getSearchStatus() == null)) {
                    Predicate paymentStatusPredicate = criteriaBuilder.like(criteriaBuilder.upper(root.get("status")), "%" + paymentSearchDTO.getSearchStatus().toUpperCase() + "%");
                    predicates.add(paymentStatusPredicate);
                    System.out.println(paymentStatusPredicate);
                }
                if (!(paymentSearchDTO.getSearchPaymentMethod() == null)) {
                    Predicate paymentMethodPredicate = criteriaBuilder.like(criteriaBuilder.upper(root.get("paymentMethod")), "%" + paymentSearchDTO.getSearchPaymentMethod().toUpperCase() + "%");
                    predicates.add(paymentMethodPredicate);
                }
                if (!(paymentSearchDTO.getSearchAmount() == null)) {
                    Predicate amountPredicate = criteriaBuilder.equal(root.get("amount"), paymentSearchDTO.getSearchAmount());
                    predicates.add(amountPredicate);
                }
                if (!(paymentSearchDTO.getSearchPaidAt() == null)) {
                    Predicate paidDatePredicate = criteriaBuilder.equal(root.get("paidAt"), paymentSearchDTO.getSearchPaidAt());
                    predicates.add(paidDatePredicate);
                }
                Predicate[] arrayPredicates = predicates.toArray(new Predicate[predicates.size()]);
                return criteriaBuilder.and(arrayPredicates);
            }
        };
    }
}