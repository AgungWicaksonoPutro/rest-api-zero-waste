package com.enigma.zerowaste.controller;

import com.enigma.zerowaste.constant.ApiUrlConstant;
import com.enigma.zerowaste.constant.ResponseMessage;
import com.enigma.zerowaste.dto.ShipperSearchDTO;
import com.enigma.zerowaste.entity.Shipper;
import com.enigma.zerowaste.service.ShipperService;
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
@RequestMapping(ApiUrlConstant.SHIPPER)
public class ShipperController {

    @Autowired
    ShipperService service;

    Response<Shipper> response = new Response<>();

    @PreAuthorize("hasRole('ADMIN')")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    @PostMapping
    public ResponseEntity<Response<Shipper>> createShipper(@RequestBody Shipper shipper){

        String message = String.format(ResponseMessage.DATA_INSERTED, "shipper");

        response.setMessage(message);
        response.setData(service.saveShipper(shipper));
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    @GetMapping("/{shipperId}")
    public Shipper getShipperById(@PathVariable String shipperId){
        return service.getShipperById(shipperId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    @PutMapping()
    public ResponseEntity<Response<Shipper>> updateShipper(@RequestBody Shipper shipper){
        Shipper updated = service.saveShipper(shipper);
        String message = String.format(ResponseMessage.DATA_UPDATED, "shipper");
        response.setMessage(message);
        response.setData(updated);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    @DeleteMapping("/{shipperId}")
    public void deleteShipperById(@PathVariable String shipperId){
        service.deleteShipper(shipperId);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    @GetMapping
    public Page<Shipper> getShipperPerPage(@RequestParam(name = "name", required = false) String name,
                                           @RequestParam(name = "description", required = false) String description,
                                           @RequestParam(name = "price", required = false) Double price,
                                           @RequestParam(name = "page", defaultValue = "0") Integer page,
                                           @RequestParam(name = "size", defaultValue = "5") Integer sizePerPage,
                                           @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
                                           @RequestParam(name = "direction", defaultValue = "ASC") String direction){
        ShipperSearchDTO shipperSearchDTO = new ShipperSearchDTO();
        shipperSearchDTO.setSearchShipperName(name);
        shipperSearchDTO.setSearchShipperDesc(description);
        shipperSearchDTO.setSearchShipperPrice(price);
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, sizePerPage, sort);
        return service.getPerPage(pageable, shipperSearchDTO);
    }
}
