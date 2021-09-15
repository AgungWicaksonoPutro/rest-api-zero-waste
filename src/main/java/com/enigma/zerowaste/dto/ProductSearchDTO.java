package com.enigma.zerowaste.dto;

import lombok.*;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@AllArgsConstructor
@NoArgsConstructor
public class ProductSearchDTO {
    private String searchProductName;
    private Integer searchProductPrice;
    private Integer searhProductStock;
}
