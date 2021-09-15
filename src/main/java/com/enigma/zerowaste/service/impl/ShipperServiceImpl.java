package com.enigma.zerowaste.service.impl;

import com.enigma.zerowaste.constant.ResponseMessage;
import com.enigma.zerowaste.dto.ShipperSearchDTO;
import com.enigma.zerowaste.entity.Product;
import com.enigma.zerowaste.entity.Shipper;
import com.enigma.zerowaste.repository.ShipperRepository;
import com.enigma.zerowaste.service.ShipperService;
import com.enigma.zerowaste.specification.ShipperSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipperServiceImpl implements ShipperService {

    @Autowired
    ShipperRepository repository;

    public String shipperValidation(String id) {
        String message="";
        if (!(repository.existsById(id))) {
            message = String.format(ResponseMessage.NOT_FOUND_MESSAGE, "shipper", id);
        }
        return message;
    }

    @Override
    public Shipper saveShipper(Shipper shipper) {
        return repository.save(shipper);
    }

    @Override
    public List<Shipper> getAllShipper() {
        return repository.findAll();
    }

    @Override
    public Shipper getShipperById(String id) {
        shipperValidation(id);
        return repository.findById(id).get();
    }

    @Override
    public void deleteShipper(String id) {
        shipperValidation(id);
        repository.deleteById(id);
    }

    @Override
    public Page<Shipper> getPerPage(Pageable pageable, ShipperSearchDTO shipperSearchDTO) {
        Specification<Shipper> shipperSpecification = ShipperSpecification.getSpecification(shipperSearchDTO);
        return repository.findAll(shipperSpecification, pageable);
    }

    @Override
    public Shipper updateShipper(String id, Shipper shipper) {
        Shipper shipperUpdate = getShipperById(id);
        shipperUpdate.setPrice(shipper.getPrice());
        shipperUpdate.setName(shipper.getName());
        shipperUpdate.setDescription(shipper.getDescription());
        return repository.save(shipperUpdate);
    }
}
