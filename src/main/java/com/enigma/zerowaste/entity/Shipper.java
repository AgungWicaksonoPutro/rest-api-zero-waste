package com.enigma.zerowaste.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mst_shipper")
public class Shipper extends Auditable<String>{

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id_shipper")
    private String id;
    private String name;
    private String description;
    private Double price;

    @OneToMany(mappedBy = "shipper", cascade = CascadeType.PERSIST)
    @JsonIgnoreProperties("shipper")
    private List<Order> orders = new ArrayList<>();

    public Shipper(String name, String description, Double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }


}
