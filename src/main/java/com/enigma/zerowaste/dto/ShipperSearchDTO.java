package com.enigma.zerowaste.dto;

import lombok.*;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@AllArgsConstructor
@NoArgsConstructor
public class ShipperSearchDTO {
    private String searchShipperName;
    private String searchShipperDesc;
    private Double searchShipperPrice;
}
