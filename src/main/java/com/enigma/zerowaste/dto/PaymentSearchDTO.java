package com.enigma.zerowaste.dto;

import lombok.*;

import java.util.Date;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@AllArgsConstructor
@NoArgsConstructor
public class PaymentSearchDTO {
    private String searchStatus;
    private String searchPaymentMethod;
    private Double searchAmount;
    private String searchPaidAt;
}
