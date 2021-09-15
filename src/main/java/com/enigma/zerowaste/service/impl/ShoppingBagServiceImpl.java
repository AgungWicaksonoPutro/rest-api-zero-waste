package com.enigma.zerowaste.service.impl;

import com.enigma.zerowaste.constant.ResponseMessage;
import com.enigma.zerowaste.dto.shoppingbag.AddToShoppingBagDTO;
import com.enigma.zerowaste.dto.shoppingbag.ShoppingBagDTO;
import com.enigma.zerowaste.dto.shoppingbag.ShoppingBagItemDTO;
import com.enigma.zerowaste.entity.Customer;
import com.enigma.zerowaste.entity.Product;
import com.enigma.zerowaste.entity.ShoppingBag;
import com.enigma.zerowaste.exception.DataNotFoundException;
import com.enigma.zerowaste.exception.DataOutOfStock;
import com.enigma.zerowaste.repository.ShoppingBagRepository;
import com.enigma.zerowaste.security.services.CustomerDetailsImpl;
import com.enigma.zerowaste.service.CustomerService;
import com.enigma.zerowaste.service.ProductService;
import com.enigma.zerowaste.service.ShoppingBagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShoppingBagServiceImpl implements ShoppingBagService {

    @Autowired
    ShoppingBagRepository shoppingBagRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    ProductService productService;

    @Override
    public ShoppingBag addToShoppingBag(AddToShoppingBagDTO addToShoppingBagDTO) {
        CustomerDetailsImpl customerDetails = (CustomerDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Customer customer = customerService.getCustomerById(customerDetails.getId());
        Product product = productService.getProductById(addToShoppingBagDTO.getProductId());
        cekStock(product, addToShoppingBagDTO.getAmount());
        Integer newStock = product.getStock() - addToShoppingBagDTO.getAmount();
        product.setStock(newStock);
        productService.updateProduct(product.getId(), product);
        ShoppingBag shoppingBag = new ShoppingBag(customer, product, addToShoppingBagDTO.getAmount());
        return shoppingBagRepository.save(shoppingBag);
    }

    @Override
    public ShoppingBag updateShoppingBag(String id, AddToShoppingBagDTO addToShoppingBagDTO) {
        ShoppingBag shoppingBag = shoppingBagRepository.findById(id).get();
        Product product = productService.getProductById(shoppingBag.getProduct().getId());
        cekStock(product, addToShoppingBagDTO.getAmount());
        transactionalStockForUpdate(product, addToShoppingBagDTO.getAmount(), shoppingBag);
        shoppingBag.setAmount(addToShoppingBagDTO.getAmount());
        return shoppingBagRepository.save(shoppingBag);
    }

    @Override
    public void deleteShoppingBag(String id) throws DataNotFoundException {
        if (!shoppingBagRepository.existsById(id)){
            String message = String.format(ResponseMessage.NOT_FOUND_MESSAGE);
            throw  new DataNotFoundException(message);
        }
        shoppingBagRepository.deleteById(id);
    }

    @Override
    public ShoppingBagDTO getAllShoppingBag() {
        CustomerDetailsImpl customerDetails = (CustomerDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Customer customer = customerService.getCustomerById(customerDetails.getId());
        List<ShoppingBag> shoppingBagList = shoppingBagRepository.findAllByCustomerOrderByCreatedDateDesc(customer);
        List<ShoppingBagItemDTO> shoppingBagItem = new ArrayList<>();
        for (ShoppingBag shoppingBag : shoppingBagList) {
            ShoppingBagItemDTO shoppingBagItemDTO = getDTOShoppingBag(shoppingBag);
            shoppingBagItem.add(shoppingBagItemDTO);
        }
        Double totalPrice = 0.0;
        for (ShoppingBagItemDTO shoppingBagItemDTO:shoppingBagItem) {
            totalPrice += (shoppingBagItemDTO.getProduct().getProductPrice() * shoppingBagItemDTO.getAmount());
        }
        ShoppingBagDTO shoppingBagDTO = new ShoppingBagDTO(shoppingBagItem, totalPrice);
        return shoppingBagDTO;
    }

    @Override
    public ShoppingBag getShoppingBag(String id) {
        return shoppingBagRepository.findById(id).get();
    }

    public ShoppingBagItemDTO getDTOShoppingBag(ShoppingBag shoppingBag){
        ShoppingBagItemDTO shoppingBagItemDTO = new ShoppingBagItemDTO(shoppingBag);
        return shoppingBagItemDTO;
    }


    public void cekStock(Product product, Integer stock){
        if (product.getStock() == 0){
            String message = String.format(ResponseMessage.OUT_OF_STOCK);
            throw new DataOutOfStock(message);
        }
        if (product.getStock() < stock){
            String message = String.format(ResponseMessage.OUT_OF_STOCK + " Product Stock: " + product.getStock());
            throw new DataOutOfStock(message);
        }
    }

    public void transactionalStockForUpdate(Product product, Integer stock, ShoppingBag shoppingBag){
        if (stock < shoppingBag.getAmount()){
            Integer newStock = product.getStock() + (shoppingBag.getAmount() - stock);
            product.setStock(newStock);
            productService.updateProduct(product.getId(), product);
        }
        if (stock > shoppingBag.getAmount()){
            Integer newStock = product.getStock() - shoppingBag.getAmount();
            product.setStock(newStock);
            productService.updateProduct(product.getId(), product);
        }
    }
}
