package com.enigma.zerowaste.specification;

import com.enigma.zerowaste.dto.CustomerSearchDTO;
import com.enigma.zerowaste.entity.Customer;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class CustomerSpesification {
    public static Specification<Customer> getSpesification(CustomerSearchDTO customerSearchDTO){
        return new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate>predicates = new ArrayList<>();
                if (!(customerSearchDTO.getSearchCustomerName()== null)){
                    Predicate customerNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("customerName")),"%"+customerSearchDTO.getSearchCustomerName().toLowerCase()+"%");
                    predicates.add(customerNamePredicate);
                }
                if (!(customerSearchDTO.getSearchCustomerPhone()== null)){
                    Predicate customerPhonePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("phone")),"%"+customerSearchDTO.getSearchCustomerPhone().toLowerCase()+"%");
                    predicates.add(customerPhonePredicate);
                }
                if (!(customerSearchDTO.getSearchCustomerEmail()== null)){
                    Predicate customerEmailPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("email")),"%"+customerSearchDTO.getSearchCustomerEmail().toLowerCase()+"%");
                    predicates.add(customerEmailPredicate);
                }
                Predicate[] arrayPredicates = predicates.toArray(new Predicate[predicates.size()]);
                return criteriaBuilder.and(arrayPredicates);
            }
        };
    }
}
