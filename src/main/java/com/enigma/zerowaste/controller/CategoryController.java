package com.enigma.zerowaste.controller;

import com.enigma.zerowaste.constant.ApiUrlConstant;
import com.enigma.zerowaste.dto.CategorySearchDTO;
import com.enigma.zerowaste.entity.Category;
import com.enigma.zerowaste.service.CategoryService;
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
@RequestMapping(ApiUrlConstant.CATEGORY)
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    public static Response response = new Response();

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    public ResponseEntity<Response<Category>> createdCategory(@RequestBody Category category){
        String message = String.format("Data resource category inserted");
        response.setMessage(message);
        response.setData(categoryService.saveCategory(category));
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{categoryId}")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    public ResponseEntity<Response<Category>> getCategoryById(@PathVariable String categoryId){
        String message = String.format("Succsess");
        response.setMessage(message);
        response.setData(categoryService.getCategoryById(categoryId));
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{categoryId}")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    public ResponseEntity<Response<Category>> updateCategory(@PathVariable String categoryId,
                                                             @RequestBody Category category){
        String message = String.format("Upadated");
        response.setMessage(message);
        response.setData(categoryService.updateProduct(categoryId, category));
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{categoryId}")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    public ResponseEntity<Response<String>> deleteCategory(@PathVariable String categoryId){
        String message = String.format("Succsess");
        String body = String.format("Category with id %s Deleted", categoryId);
        categoryService.deleteProduct(categoryId);
        response.setMessage(message);
        response.setData(body);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    public ResponseEntity<Response<Pagination<Category>>> getAllCategory(@RequestParam(name = "keyword", defaultValue = "", required = false) CategorySearchDTO categorySearchDTO,
                                                                         @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                                         @RequestParam(name = "size", defaultValue = "3") Integer sizePage,
                                                                         @RequestParam(name = "sortBy", defaultValue = "nameCategory") String sortBy,
                                                                         @RequestParam(name = "direction", defaultValue = "ASC") String direction){
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, sizePage, sort);
        Page<Category> categoryPage = categoryService.getAllCategory(pageable, categorySearchDTO);
        Pagination<Category> pagination = new Pagination<Category>(categoryPage);
        Response<Pagination<Category>> response = new Response<>();
        response.setMessage("Succsess");
        response.setData(pagination);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}
