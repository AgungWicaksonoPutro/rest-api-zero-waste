package com.enigma.zerowaste.service;

import com.enigma.zerowaste.dto.AddressDTO;
import com.enigma.zerowaste.dto.AddressSearchDTO;
import com.enigma.zerowaste.entity.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AddressService {
    public Address saveAddress(Address address);
    public Address saveAddressDto(AddressDTO addressDTO);
    public Address getAddressById(String id);
    public Address updateAddress(String id,Address address);
    public Address deleteAddress(String id);
    public Page<Address> getAddressPerPage(Pageable pageable, AddressSearchDTO addressSearchDTO);
}
