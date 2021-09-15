package com.enigma.zerowaste.service.impl;

import com.enigma.zerowaste.constant.ResponseMessage;
import com.enigma.zerowaste.dto.AddressDTO;
import com.enigma.zerowaste.dto.AddressSearchDTO;
import com.enigma.zerowaste.entity.Address;
import com.enigma.zerowaste.entity.Customer;
import com.enigma.zerowaste.exception.DataNotFoundException;
import com.enigma.zerowaste.repository.AddressRepository;
import com.enigma.zerowaste.security.services.CustomerDetailsImpl;
import com.enigma.zerowaste.service.AddressService;
import com.enigma.zerowaste.service.CustomerService;
import com.enigma.zerowaste.specification.AddressSpesification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;


@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    CustomerService customerService;

    @Override
    public Address saveAddress(Address address) {
        CustomerDetailsImpl customerDetails = (CustomerDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Customer customer = customerService.getCustomerById(customerDetails.getId());

        address.setCustomer(customer);
        Address addressSaved = addressRepository.save(address);
        return addressSaved;
    }

    public Address saveAddressDto(AddressDTO addressDTO) {
        Address address = new Address();
        CustomerDetailsImpl customerDetails = (CustomerDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Customer customer = customerService.getCustomerById(customerDetails.getId());
        address.setCustomer(customer);
        address.setDescriptions(addressDTO.getDescriptions());
        address.setName(addressDTO.getName());
        address.setPhoneNumber(addressDTO.getPhoneNumber());
        Address addresssaved = addressRepository.save(address);
        return addresssaved;
    }

    @Override
    public Address getAddressById(String id) {
        validatePresent(id);
        return addressRepository.findById(id).get();
    }

    @Override
    public Address updateAddress(String id, Address address) {
        Address addressUpdated = getAddressById(id);
        address.setId(id);
        addressUpdated = addressRepository.save(address);
        return addressUpdated;
    }


    @Override
    public Address deleteAddress(String id) {
        Address addressDeleted = getAddressById(id);
        addressDeleted.setIsdeleted(true);
        addressRepository.save(addressDeleted);
        return addressDeleted;
    }

    @Override
    public Page<Address> getAddressPerPage(Pageable pageable, AddressSearchDTO addressSearchDTO) {
        Specification<Address> addressSpecification = AddressSpesification.getSpesification(addressSearchDTO);
        return addressRepository.findAll(addressSpecification, pageable);
    }

    private void validatePresent(String id) {
        if (!addressRepository.findById(id).isPresent()) {
            String message = String.format(ResponseMessage.NOT_FOUND_MESSAGE, "Address", id);
            throw new DataNotFoundException(message);
        }
    }
}
