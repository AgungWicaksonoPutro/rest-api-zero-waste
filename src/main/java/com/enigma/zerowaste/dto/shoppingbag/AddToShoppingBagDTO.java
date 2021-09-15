package com.enigma.zerowaste.dto.shoppingbag;

import lombok.*;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@NoArgsConstructor
public class AddToShoppingBagDTO {
    private String productId;
    private Integer amount;

    public AddToShoppingBagDTO(String productId, Integer amount) {
        this.productId = productId;
        this.amount = amount;
    }
}
