package com.enigma.zerowaste.dto.invoice;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@AllArgsConstructor
public class InvoiceDTO {
    public String externalId;
    public Double amount;
    public String payerEmail;
    public String descriptionInvoice;

    @Override
    public String toString() {
        return "InvoiceDTO{" +
                "externalId='" + externalId + '\'' +
                ", amount=" + amount +
                ", payerEmail='" + payerEmail + '\'' +
                ", descriptionInvoice='" + descriptionInvoice + '\'' +
                '}';
    }
}
