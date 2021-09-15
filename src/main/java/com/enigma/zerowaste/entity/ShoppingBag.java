package com.enigma.zerowaste.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "trx_shopping_bag")
public class ShoppingBag extends Auditable<String>{
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id_shopping_bag")
    private String id;
    @ManyToOne()
    @JoinColumn(name = "customer_id")
    @JsonIgnoreProperties("shoppingBag")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product;
    private Integer amount;


    public ShoppingBag(Customer customer, Product product, Integer amount) {
        this.customer = customer;
        this.product = product;
        this.amount = amount;
    }

}
