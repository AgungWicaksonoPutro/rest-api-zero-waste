package com.enigma.zerowaste.specification;

import com.enigma.zerowaste.dto.ShipperSearchDTO;
import com.enigma.zerowaste.entity.Shipper;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ShipperSpecification {
    public static Specification<Shipper> getSpecification(ShipperSearchDTO shipperSearchDTO){
        return new Specification<Shipper>() {
            @Override
            public Predicate toPredicate(Root<Shipper> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!(shipperSearchDTO.getSearchShipperName() == null)){
                    Predicate shipperNamePredicate = criteriaBuilder.like(criteriaBuilder.upper(root.get("name")), "%"+shipperSearchDTO.getSearchShipperName().toUpperCase()+"%");
                    predicates.add(shipperNamePredicate);
                }if (!(shipperSearchDTO.getSearchShipperDesc() == null)){
                    Predicate shipperDescPredicate = criteriaBuilder.like(criteriaBuilder.upper(root.get("description")), "%"+shipperSearchDTO.getSearchShipperDesc().toUpperCase()+"%");
                    predicates.add(shipperDescPredicate);
                }if (!(shipperSearchDTO.getSearchShipperPrice() == null)){
                    Predicate shipperPricePredicate = criteriaBuilder.equal((root.get("price")), shipperSearchDTO.getSearchShipperPrice());
                    predicates.add(shipperPricePredicate);
                }
                Predicate[] arrayPredicates = predicates.toArray(new Predicate[predicates.size()]);
                return criteriaBuilder.and(arrayPredicates);
            }
        };
    }
}
