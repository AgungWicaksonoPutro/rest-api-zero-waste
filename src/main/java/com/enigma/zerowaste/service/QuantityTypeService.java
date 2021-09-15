package com.enigma.zerowaste.service;

import com.enigma.zerowaste.dto.QuantityTypeSearchDTO;
import com.enigma.zerowaste.entity.QuantityType;
import com.enigma.zerowaste.entity.Shipper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QuantityTypeService {
    public QuantityType saveQType(QuantityType quantityType);
    public List<QuantityType> getAllQType();
    public QuantityType getQTypeById(String id);
    public void deleteQuantityType(String id);
    public QuantityType updateQType(String id, QuantityType quantityType);
    public Page<QuantityType> getPerPage(Pageable pageable, QuantityTypeSearchDTO quantityTypeSearchDTO);
}
