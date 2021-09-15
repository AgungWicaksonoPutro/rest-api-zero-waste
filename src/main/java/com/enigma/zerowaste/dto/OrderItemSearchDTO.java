package com.enigma.zerowaste.dto;

import com.enigma.zerowaste.entity.Order;
import lombok.*;
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemSearchDTO {
    private Integer searchAmount;
    private Float searchPrice;
}