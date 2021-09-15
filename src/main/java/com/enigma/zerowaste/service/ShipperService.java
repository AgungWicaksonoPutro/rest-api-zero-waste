package com.enigma.zerowaste.service;

import com.enigma.zerowaste.dto.ShipperSearchDTO;
import com.enigma.zerowaste.entity.Shipper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ShipperService {
    public Shipper saveShipper(Shipper shipper);
    public List<Shipper> getAllShipper();
    public Shipper getShipperById(String id);
    public void deleteShipper(String id);
    public Shipper updateShipper(String id, Shipper shipper);
    public Page<Shipper> getPerPage(Pageable pageable, ShipperSearchDTO shipperSearchDTO);
}
