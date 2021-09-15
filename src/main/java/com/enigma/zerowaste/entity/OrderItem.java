package com.enigma.zerowaste.entity;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "trx_order_items")
public class OrderItem extends Auditable<String>{
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    @Column(name = "id_order_items")
    private String id;
    private Integer amount;
    private Float price;

    @ManyToOne
    @JoinColumn(name = "id_order")
    private Order order;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_product")
    private Product product;

    public OrderItem(Integer amount, Float price, Order order, Product product) {
        this.amount = amount;
        this.price = price;
        this.order = order;
        this.product = product;
    }
}
