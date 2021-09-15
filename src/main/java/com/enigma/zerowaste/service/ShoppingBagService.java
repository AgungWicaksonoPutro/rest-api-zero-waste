package com.enigma.zerowaste.service;

import com.enigma.zerowaste.dto.shoppingbag.AddToShoppingBagDTO;
import com.enigma.zerowaste.dto.shoppingbag.ShoppingBagDTO;
import com.enigma.zerowaste.entity.Customer;
import com.enigma.zerowaste.entity.Product;
import com.enigma.zerowaste.entity.ShoppingBag;
import com.enigma.zerowaste.exception.DataNotFoundException;

import java.util.List;

public interface ShoppingBagService {
    public ShoppingBag addToShoppingBag(AddToShoppingBagDTO addToShoppingBagDTO);
    public ShoppingBag updateShoppingBag(String id, AddToShoppingBagDTO addToShoppingBagDTO);
    public void deleteShoppingBag(String id) throws DataNotFoundException;
    public ShoppingBagDTO getAllShoppingBag();
    public ShoppingBag getShoppingBag(String id);
}
