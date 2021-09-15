package com.enigma.zerowaste.specification;

import com.enigma.zerowaste.dto.QuantityTypeSearchDTO;
import com.enigma.zerowaste.entity.QuantityType;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class QuantityTypeSpecification {
    public static Specification<QuantityType> getSpecification(QuantityTypeSearchDTO quantityTypeSearchDTO){
        return new Specification<QuantityType>() {
            @Override
            public Predicate toPredicate(Root<QuantityType> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!(quantityTypeSearchDTO.getSearchQTypeName() == null)){
                    Predicate qTypeNamePredicate = criteriaBuilder.like(root.get("name"), "%"+ quantityTypeSearchDTO.getSearchQTypeName()+"%");
                    predicates.add(qTypeNamePredicate);
                }
                if (!(quantityTypeSearchDTO.getSearchQTypeDescription() == null)){
                    Predicate qTypeDescPredicate = criteriaBuilder.like(root.get("description"), "%"+ quantityTypeSearchDTO.getSearchQTypeDescription()+"%");
                    predicates.add(qTypeDescPredicate);
                }

                Predicate[] arrayPredicates = predicates.toArray(new Predicate[predicates.size()]);
                return criteriaBuilder.and(arrayPredicates);
            }
        };
    }
}
