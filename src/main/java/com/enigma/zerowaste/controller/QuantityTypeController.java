package com.enigma.zerowaste.controller;

import com.enigma.zerowaste.constant.ApiUrlConstant;
import com.enigma.zerowaste.constant.ResponseMessage;
import com.enigma.zerowaste.dto.QuantityTypeSearchDTO;
import com.enigma.zerowaste.entity.QuantityType;
import com.enigma.zerowaste.service.QuantityTypeService;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiUrlConstant.QUANTITY_TYPE)
public class QuantityTypeController {
    @Autowired
    QuantityTypeService service;

    Response<QuantityType> response = new Response<>();

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    public ResponseEntity<Response<QuantityType>> createQType(@RequestBody QuantityType quantityType) {

        String message = String.format(ResponseMessage.DATA_INSERTED, "quantity type");

        response.setMessage(message);
        response.setData(service.saveQType(quantityType));
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    public List<QuantityType> getAllQTypes() {
        return service.getAllQType();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{qTypeId}")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    public QuantityType getQTypeById(@PathVariable String qTypeId){
        return service.getQTypeById(qTypeId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping()
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    public ResponseEntity<Response<QuantityType>> updateQType(@RequestBody QuantityType quantityType) {
        QuantityType updated = service.saveQType(quantityType);
        String message = String.format(ResponseMessage.DATA_UPDATED, "quantity type");
        response.setMessage(message);
        response.setData(updated);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{qTypeId}")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    public void deleteQTypeById(@PathVariable String qTypeId) {
        service.deleteQuantityType(qTypeId);
    }


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    public Page<QuantityType> getQTypePerPage(@RequestParam(name = "name", required = false) String name,
                                              @RequestParam(name = "description", required = false) String description,
                                              @RequestParam(name = "page", defaultValue = "1") Integer page,
                                              @RequestParam(name = "size", defaultValue = "5") Integer sizePerPage,
                                              @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
                                              @RequestParam(name = "direction", defaultValue = "ASC") String direction) {
        QuantityTypeSearchDTO quantityTypeSearchDTO = new QuantityTypeSearchDTO();
        quantityTypeSearchDTO.setSearchQTypeDescription(description);
        quantityTypeSearchDTO.setSearchQTypeName(name);
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, sizePerPage, sort);
        return service.getPerPage(pageable, quantityTypeSearchDTO);
    }
}
