package com.enigma.zerowaste.dto;

import lombok.*;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@AllArgsConstructor
@NoArgsConstructor
public class QuantityTypeSearchDTO {
    private String searchQTypeName;
    private String searchQTypeDescription;
}
