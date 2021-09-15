package com.enigma.zerowaste.dto.order;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@AllArgsConstructor
public class ResponseOrderDTO {
    String idOrder;
    Double amount;
    String merchanName;
    String urlInvoice;
}
