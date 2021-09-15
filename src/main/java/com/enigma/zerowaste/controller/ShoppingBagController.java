package com.enigma.zerowaste.controller;

import com.enigma.zerowaste.constant.ApiUrlConstant;
import com.enigma.zerowaste.constant.ResponseMessage;
import com.enigma.zerowaste.dto.shoppingbag.AddToShoppingBagDTO;
import com.enigma.zerowaste.dto.shoppingbag.ShoppingBagItemDTO;
import com.enigma.zerowaste.entity.ShoppingBag;
import com.enigma.zerowaste.security.services.CustomerDetailsImpl;
import com.enigma.zerowaste.service.ShoppingBagService;
import com.enigma.zerowaste.utils.Response;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiUrlConstant.ADD_SHOPPING_BAG)
public class ShoppingBagController {
    private static Response response = new Response();

    @Autowired
    private ShoppingBagService shoppingBagService;


    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    @PostMapping
    public ResponseEntity<Response<ShoppingBag>> createShoppingBag(@RequestBody AddToShoppingBagDTO addToShoppingBagDTO){
        String message = String.format(ResponseMessage.DATA_INSERTED, "Shopping Bag");
        response.setMessage(message);
        response.setData(shoppingBagService.addToShoppingBag(addToShoppingBagDTO));
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    @PutMapping("/{idShoppingBag}")
    public ResponseEntity<Response<ShoppingBag>> updateShoppingBag(@PathVariable String idShoppingBag,
                                                                   @RequestBody AddToShoppingBagDTO addToShoppingBagDTO){
        String message = String.format(ResponseMessage.DATA_UPDATED, "Shopping Bag");
        response.setMessage(message);
        response.setData(shoppingBagService.updateShoppingBag(idShoppingBag, addToShoppingBagDTO));
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    @DeleteMapping("/{idShoppingBag}")
    public ResponseEntity<Response<String>> deleteShoppingBag(@PathVariable String idShoppingBag){
        String message = String.format(ResponseMessage.DATA_DELETED, idShoppingBag);
        response.setMessage(message);
        shoppingBagService.deleteShoppingBag(idShoppingBag);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    @GetMapping
    public ResponseEntity<Response<ShoppingBagItemDTO>> getAllShoppingBag(){
        String message = String.format("Succsess");
        response.setMessage(message);
        response.setData(shoppingBagService.getAllShoppingBag());
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}
