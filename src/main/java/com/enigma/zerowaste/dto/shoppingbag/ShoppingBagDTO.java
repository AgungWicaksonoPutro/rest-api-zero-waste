package com.enigma.zerowaste.dto.shoppingbag;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class ShoppingBagDTO {
    private List<ShoppingBagItemDTO> shoppingBagItems;
    private Double totalPrice;

    @Override
    public String toString() {
        return "ShoppingBagDTO{" +
                "shoppingBagItems=" + shoppingBagItems +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
