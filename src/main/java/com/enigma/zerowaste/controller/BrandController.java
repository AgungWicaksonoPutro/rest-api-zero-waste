package com.enigma.zerowaste.controller;

import com.enigma.zerowaste.constant.ApiUrlConstant;
import com.enigma.zerowaste.dto.BrandSearchDTO;
import com.enigma.zerowaste.entity.Brand;
import com.enigma.zerowaste.service.BrandService;
import com.enigma.zerowaste.utils.Pagination;
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

@RestController
@RequestMapping(ApiUrlConstant.BRAND)
public class BrandController {

    @Autowired
    BrandService brandService;

    public static Response response = new Response();

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    public ResponseEntity<Response<Brand>> createBrand(@RequestBody Brand brand){
        String message = String.format("Data resource brand inserted");
        response.setMessage(message);
        response.setData(brandService.saveBrand(brand));
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{brandId}")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    public ResponseEntity<Response<Brand>> getBrandById(@PathVariable String brandId){
        String message = String.format("Success");
        response.setMessage(message);
        response.setData(brandService.getBrandById(brandId));
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{brandId}")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    public ResponseEntity<Response<Brand>> updateBrand(@PathVariable String brandId,
                                                       @RequestBody Brand brand){
        String message = String.format("Updated");
        response.setMessage(message);
        response.setData(brandService.updateBrand(brandId, brand));
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{brandId}")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    public ResponseEntity<Response<String>> deleteBrand(@PathVariable String brandId){
        String message = String.format("Success");
        String body = String.format("Product %s Deleted", brandId);
        response.setMessage(message);
        response.setData(body);
        brandService.deleteBrand(brandId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = false, dataType = "string", paramType = "header")})
    public ResponseEntity<Response<Pagination<Brand>>> getAllBrand(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                                   @RequestParam(name = "keyword", defaultValue = "", required = false) String brandName,
                                                                   @RequestParam(name = "size", defaultValue = "3") Integer sizePage,
                                                                   @RequestParam(name = "sortBy", defaultValue = "nameBrand") String sortBy,
                                                                   @RequestParam(name = "direction", defaultValue = "ASC") String direction){
        BrandSearchDTO brandSearchDTO = new BrandSearchDTO(brandName);
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, sizePage, sort);
        Page<Brand> brandPage =brandService.getAllBrand(pageable, brandSearchDTO);
        Pagination<Brand> pagination = new Pagination<Brand>(brandPage);
        Response<Pagination<Brand>> response = new Response<>();
        response.setMessage("Succsess");
        response.setData(pagination);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}
