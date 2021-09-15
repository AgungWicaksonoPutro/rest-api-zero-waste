package com.enigma.zerowaste.service.impl;

import com.enigma.zerowaste.constant.ResponseMessage;
import com.enigma.zerowaste.dto.QuantityTypeSearchDTO;
import com.enigma.zerowaste.entity.Product;
import com.enigma.zerowaste.entity.QuantityType;
import com.enigma.zerowaste.entity.Shipper;
import com.enigma.zerowaste.repository.QuantityTypeRepository;
import com.enigma.zerowaste.service.QuantityTypeService;
import com.enigma.zerowaste.specification.QuantityTypeSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuantityTypeServiceImpl implements QuantityTypeService {

    @Autowired
    QuantityTypeRepository repository;

    public String qTypeValidation(String id) {
        String message="";
        if (!(repository.existsById(id))) {
            message = String.format(ResponseMessage.NOT_FOUND_MESSAGE, "quantity type", id);
        }
        return message;
    }

    @Override
    public QuantityType saveQType(QuantityType quantityType) {
        return repository.save(quantityType);
    }

    @Override
    public List<QuantityType> getAllQType() {
        return repository.findAll();
    }

    @Override
    public QuantityType getQTypeById(String id) {
        qTypeValidation(id);
        return repository.findById(id).get();
    }

    @Override
    public void deleteQuantityType(String id) {
        repository.deleteById(id);
    }

    @Override
    public QuantityType updateQType(String id, QuantityType quantityType) {
        QuantityType qTypeUpdate = getQTypeById(id);
        qTypeUpdate.setName(qTypeUpdate.getName());
        qTypeUpdate.setDescription(qTypeUpdate.getDescription());
        return repository.save(qTypeUpdate);
    }

    @Override
    public Page<QuantityType> getPerPage(Pageable pageable, QuantityTypeSearchDTO quantityTypeSearchDTO) {
        Specification<QuantityType> quantityTypeSpecification = QuantityTypeSpecification.getSpecification(quantityTypeSearchDTO);
        return repository.findAll(quantityTypeSpecification, pageable);
    }
}
