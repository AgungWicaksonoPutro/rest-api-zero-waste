package com.enigma.zerowaste.dto.order;

import com.enigma.zerowaste.entity.ShoppingBag;
import lombok.*;

import java.util.List;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    String idShipper;
    List<String> idShoppingBags;

}
