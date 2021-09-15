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
@Table(name = "mst_category")
public class Category extends Auditable<String> {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "category_id")
    private String id;
    private String nameCategory;
    @Column(columnDefinition = "TEXT")
    private String description;
    private Integer isActive;
    @OneToMany(targetEntity = Product.class, mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("category")
    private List<Product> products = new ArrayList<>();

    public Category(String id, String nameCategory, String description, Integer isActive) {
        this.id = id;
        this.nameCategory = nameCategory;
        this.description = description;
        this.isActive = isActive;
    }
}
