package com.enigma.zerowaste.dto.shoppingbag;

import com.enigma.zerowaste.entity.Product;
import com.enigma.zerowaste.entity.ShoppingBag;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class ShoppingBagItemDTO {
    private String id;
    private String userId;
    private Integer amount;
    private Product product;

    public ShoppingBagItemDTO(ShoppingBag shoppingBag){
        this.id = shoppingBag.getId();
        this.userId = shoppingBag.getCustomer().getId();
        this.amount = shoppingBag.getAmount();
        this.product = shoppingBag.getProduct();
    }

    @Override
    public String toString() {
        return "ShoppingBagItemDTO{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", amount=" + amount +
                ", product=" + product +
                '}';
    }
}
