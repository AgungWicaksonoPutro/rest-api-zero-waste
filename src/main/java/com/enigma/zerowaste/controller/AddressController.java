package com.enigma.zerowaste.controller;

import com.enigma.zerowaste.constant.ApiUrlConstant;
import com.enigma.zerowaste.constant.ResponseMessage;
import com.enigma.zerowaste.dto.AddressDTO;
import com.enigma.zerowaste.dto.AddressSearchDTO;
import com.enigma.zerowaste.entity.Address;
import com.enigma.zerowaste.entity.Customer;
import com.enigma.zerowaste.service.AddressService;
import com.enigma.zerowaste.utils.PageResponseWrapper;
import com.enigma.zerowaste.utils.Response;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Locale;

@RestController
@RequestMapping(ApiUrlConstant.ADDRESS)
public class AddressController {

    @Autowired
    AddressService addressService;

    public static Response<Address> response = new Response();

    @PostMapping
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    @PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
    public ResponseEntity<Response<Address>> createAddress(@Valid @RequestBody AddressDTO address, Errors errors) {
        if (errors.hasErrors()){
            for (ObjectError error : errors.getAllErrors()){
                response.getMessages().add(error.getDefaultMessage());
            }
            String message = String.format(ResponseMessage.DATA_FAILED, "Address");
            response.setMessage(message);
            response.setStatus(false);
            response.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }else {
            String message = String.format(ResponseMessage.DATA_INSERTED, "Address");
            response.setMessage(message);
            response.setData(addressService.saveAddressDto(address));
            return ResponseEntity.status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        }
    }

    @GetMapping("/{id}")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Response<Address>> getAddressById(@PathVariable String id) {
        String message = String.format(ResponseMessage.GET_DATA, "Address", id);
        response.setMessage(message);
        response.setData(addressService.getAddressById(id));
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }


    @PutMapping("/{id}")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Response<Address>> updateAddressById(@PathVariable String id, @RequestBody Address address) {
        String message = String.format(ResponseMessage.DATA_UPDATED, "Address");
        response.setMessage(message);
        response.setData(addressService.updateAddress(id, address));
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @DeleteMapping("/{id}")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Response<Address>> deleteAddressById(@PathVariable String id) {
        String message = String.format(ResponseMessage.DATA_DELETED, "Address");
        response.setMessage(message);
        response.setData(addressService.deleteAddress(id));
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public PageResponseWrapper<Address> searchAddressPerPage(@RequestParam(name = "name", defaultValue = "") String name,
                                                             @RequestParam(name = "phoneNumber", defaultValue = "", required = false) String phoneNumber,
                                                             @RequestParam(name = "descriptions", defaultValue = "", required = false) String descriptions,
                                                             @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                             @RequestParam(name = "size", defaultValue = "3") Integer sizePerPage,
                                                             @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
                                                             @RequestParam(name = "direction", defaultValue = "ASC") String direction
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, sizePerPage, sort);
        AddressSearchDTO addressSearchDTO = new AddressSearchDTO(name, phoneNumber, descriptions);

        Page<Address> addressPage = addressService.getAddressPerPage(pageable, addressSearchDTO);
        return new PageResponseWrapper<Address>(addressPage);
    }
}
