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
@Table(name = "mst_quantity_type")
public class QuantityType extends Auditable<String> {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id_quantity_type")
    private String id;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "quantityType")
    @JsonIgnoreProperties("quantityType")
    private List<Product> productList = new ArrayList<>();

    public QuantityType(String name, String description, List<Product> productList) {
        this.name = name;
        this.description = description;
        this.productList = productList;
    }
}
