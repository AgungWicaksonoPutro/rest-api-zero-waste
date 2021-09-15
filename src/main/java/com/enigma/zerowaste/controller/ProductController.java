package com.enigma.zerowaste.controller;

import com.enigma.zerowaste.constant.ApiUrlConstant;
import com.enigma.zerowaste.constant.ResponseMessage;
import com.enigma.zerowaste.dto.ProductSearchDTO;
import com.enigma.zerowaste.entity.Brand;
import com.enigma.zerowaste.entity.Category;
import com.enigma.zerowaste.entity.Product;
import com.enigma.zerowaste.entity.QuantityType;
import com.enigma.zerowaste.service.*;
import com.enigma.zerowaste.utils.Response;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(ApiUrlConstant.PRODUCT)
public class ProductController {

    @Autowired
    ProductService service;

    @Autowired
    CategoryService categoryService;

    @Autowired
    BrandService brandService;

    @Autowired
    QuantityTypeService quantityTypeService;

    @Autowired
    ImageService imageService;

    @Value("${zerowaste.app.baseURL}")
    private String baseURL;

    Response<Product> response = new Response<>();

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})

    public ResponseEntity<Response<Product>> createProduct(@RequestParam(name = "productName") String productName,
                                                           @RequestParam(name = "productPrice") Float productPrice,
                                                           @RequestParam(name = "descriptions") String descriptions,
                                                           @RequestParam(name = "stock") Integer stock,
                                                           @RequestPart(name = "productImage") MultipartFile productImage,
                                                           @RequestParam(name = "brand") String brand,
                                                           @RequestParam(name = "quantityType") String quantityType,
                                                           @RequestParam(name = "category") String category) {
        Category category1 = categoryService.getCategoryById(category);
        Brand brand1 = brandService.getBrandById(brand);
        QuantityType quantityType1 = quantityTypeService.getQTypeById(quantityType);
        String fileName = StringUtils.cleanPath(productImage.getOriginalFilename());
        String urlImage = baseURL + fileName;

        try {
            imageService.storeToServe(productImage.getBytes(), fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Product product = new Product(productName, productPrice, descriptions, stock, urlImage, brand1, category1, quantityType1);
        String message = String.format(ResponseMessage.DATA_INSERTED, "product");
        response.setMessage(message);
        response.setData(service.saveProduct(product));
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{productId}")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    public Product getProductById(@PathVariable String productId) {
        return service.getProductById(productId);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping()
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    public ResponseEntity<Response<Product>> updateProduct(@RequestBody Product product) {
        Product updated = service.saveProduct(product);
        String message = String.format(ResponseMessage.DATA_UPDATED, "product");
        response.setMessage(message);
        response.setData(updated);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{productId}")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    public void deleteProductById(@PathVariable String productId) {
        service.deleteProduct(productId);

    }


    @GetMapping
    public Page<Product> getProductPerPage(@RequestParam(name = "productName", required = false) String name,
                                           @RequestParam(name = "price", required = false) Integer price,
                                           @RequestParam(name = "stock", required = false) Integer stock,
                                           @RequestParam(name = "page", defaultValue = "0") Integer page,
                                           @RequestParam(name = "size", defaultValue = "5") Integer sizePerPage,
                                           @RequestParam(name = "sortBy", defaultValue = "productName") String sortBy,
                                           @RequestParam(name = "direction", defaultValue = "ASC") String direction) {
        ProductSearchDTO productSearchDTO = new ProductSearchDTO();
        productSearchDTO.setSearchProductName(name);
        productSearchDTO.setSearhProductStock(stock);
        productSearchDTO.setSearchProductPrice(price);
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, sizePerPage, sort);
        return service.getPerPage(pageable, productSearchDTO);
    }
}
