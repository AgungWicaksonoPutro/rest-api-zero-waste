package com.enigma.zerowaste.controller;

import com.enigma.zerowaste.constant.ApiUrlConstant;
import com.enigma.zerowaste.constant.ResponseMessage;
import com.enigma.zerowaste.dto.CustomerSearchDTO;
import com.enigma.zerowaste.entity.Customer;

import com.enigma.zerowaste.security.jwt.AuthEntryPointJwt;

import com.enigma.zerowaste.entity.ERole;
import com.enigma.zerowaste.entity.Role;

import com.enigma.zerowaste.service.CustomerService;
import com.enigma.zerowaste.service.RoleService;
import com.enigma.zerowaste.utils.PageResponseWrapper;
import com.enigma.zerowaste.utils.Response;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(ApiUrlConstant.CUSTOMER)
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @Autowired

    PasswordEncoder passwordEncoder;

    Response<Customer> response = new Response();

    @Autowired
    RoleService roleService;


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    public ResponseEntity<Response<Customer>> createCustomer( @RequestBody Customer customer) {
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        String message = String.format(ResponseMessage.DATA_INSERTED, "Customer");
        response.setMessage(message);
        Set<Role> getRole = customer.getRoles();
        System.out.println(getRole);
        Set<String> strRoles = Collections.singleton(customer.getRoles().toString());
        System.out.println(strRoles);
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleService.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException(message));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleService.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException(message));
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleService.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException(message));
                        roles.add(userRole);
                }
            });
        }
        customer.setRoles(roles);
        response.setData(customerService.saveCustomer(customer));
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    @GetMapping("/{id}")
    public ResponseEntity<Response<Customer>> getCustomerById(@PathVariable String id) {
        String message = String.format(ResponseMessage.GET_DATA, "Customer", id);
        response.setMessage(message);
        response.setData(customerService.getCustomerById(id));
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('USER')")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    @PutMapping("/{id}")
    public ResponseEntity<Response<Customer>> updateCustomer(@PathVariable String id, @RequestBody Customer customer) {
        String message = String.format(ResponseMessage.DATA_UPDATED, "Customer");
        response.setMessage(message);
        response.setData(customerService.updateCustomer(id, customer));
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    public ResponseEntity<Response<Customer>> deleteCustomer(@PathVariable String id) {
        String message = String.format(ResponseMessage.DATA_DELETED, "Customer");
        response.setMessage(message);
        response.setData(customerService.deleteCustomer(id));
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    public PageResponseWrapper<Customer> searchCustomerPerPage(@RequestParam(name = "customerName", defaultValue = "", required = false) String customerName,
                                                               @RequestParam(name = "phone", defaultValue = "", required = false) String phone,
                                                               @RequestParam(name = "email", defaultValue = "", required = false) String email,
                                                               @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                               @RequestParam(name = "size", defaultValue = "3") Integer sizePerPage,
                                                               @RequestParam(name = "sortBy", defaultValue = "customerName") String sortBy,
                                                               @RequestParam(name = "direction", defaultValue = "ASC") String direction) {

        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, sizePerPage, sort);
        CustomerSearchDTO customerSearchDTO = new CustomerSearchDTO(customerName, phone, email);
        Page<Customer> customerPage = customerService.getCustomerPerPage(pageable, customerSearchDTO);
        return new PageResponseWrapper<Customer>(customerPage);
    }
}
