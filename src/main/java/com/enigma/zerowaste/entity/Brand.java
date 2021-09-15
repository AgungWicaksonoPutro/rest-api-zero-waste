package com.enigma.zerowaste.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mst_brand")
public class Brand extends Auditable<String> {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "brand_id")
    private String id;
    private String nameBrand;
    @Column(columnDefinition = "TEXT")
    private String description;
    @OneToMany(targetEntity = Product.class, mappedBy = "brand", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("brand")
    private List<Product> products = new ArrayList<>();

    public Brand(String id, String nameBrand, String description) {
        this.id = id;
        this.nameBrand = nameBrand;
        this.description = description;
    }
}
