package com.enigma.zerowaste.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "trx_order")
public class Order extends Auditable<String>{
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    @Column(name = "id_order")
    private String id;
    private String emailCustomer;
    @Column(name = "final_price")
    private Double finalPrice;
    private String status;
    private Double costContainer;

    @ManyToOne
    @JoinColumn(name = "id_shipper")
    @JsonIgnoreProperties("orders")
    private Shipper shipper;

    @OneToMany(mappedBy = "order")
    @JsonIgnoreProperties("order")
    private List<OrderItem> orderItems;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_payment")
    @JsonIgnoreProperties("order")
    private Payment payment;

    public Order(String emailCustomer, Double finalPrice, String status, Shipper shipper, Customer customer, Payment payment, Double costContainer) {
        this.emailCustomer = emailCustomer;
        this.finalPrice = finalPrice;
        this.status = status;
        this.shipper = shipper;
        this.customer = customer;
        this.payment = payment;
        this.costContainer = costContainer;
    }
}
