package com.enigma.zerowaste.dto.order;

import lombok.*;

import java.util.Date;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@AllArgsConstructor
@NoArgsConstructor
public class OrderSearchDTO {
    private String searchOrderId;
}
