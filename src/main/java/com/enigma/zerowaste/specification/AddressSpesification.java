package com.enigma.zerowaste.specification;

import com.enigma.zerowaste.dto.AddressSearchDTO;
import com.enigma.zerowaste.entity.Address;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class AddressSpesification {
    public static Specification<Address> getSpesification(AddressSearchDTO addressSearchDTO){
        return new Specification<Address>() {
            @Override
            public Predicate toPredicate(Root<Address> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                if (!(addressSearchDTO.getSearchAddressName()== null)){
                    Predicate addressNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),"%"+addressSearchDTO.getSearchAddressName().toLowerCase()+"%");
                    predicates.add(addressNamePredicate);
                }
                if (!(addressSearchDTO.getSearchAddressPhone()== null)){
                    Predicate addressPhonePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("phoneNumber")),"%"+addressSearchDTO.getSearchAddressPhone().toLowerCase()+"%");
                    predicates.add(addressPhonePredicate);
                }
                if (!(addressSearchDTO.getSearchAddressDescriptions()== null)){
                    Predicate addressDescriptionsPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("descriptions")),"%"+addressSearchDTO.getSearchAddressDescriptions().toLowerCase()+"%");
                    predicates.add(addressDescriptionsPredicate);
                }
                Predicate[] arrayPredicates = predicates.toArray(new Predicate[predicates.size()]);
                return criteriaBuilder.and(arrayPredicates);
            }
        };
    }
}
