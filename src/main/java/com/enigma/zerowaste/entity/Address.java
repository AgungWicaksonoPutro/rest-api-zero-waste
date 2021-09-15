package com.enigma.zerowaste.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mst_address")

public class Address extends Auditable<String>{
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id_address")
    private String id;
    private String name;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(columnDefinition = "TEXT")
    private String descriptions;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnoreProperties("addresses")
    private Customer customer;
    @Column(name = "is_deleted")
    private Boolean isdeleted = false;
}
