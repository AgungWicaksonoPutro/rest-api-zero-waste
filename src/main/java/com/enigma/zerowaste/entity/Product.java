package com.enigma.zerowaste.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter (AccessLevel.PUBLIC)
@Setter (AccessLevel.PUBLIC)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mst_product")
public class Product extends Auditable<String>{
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id_product")
    private String id;
    private String productName;
    private Float productPrice;
    @Column(columnDefinition = "TEXT")
    private String descriptions;
    private Integer stock;
    private String productImage;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    @JsonIgnoreProperties("products")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnoreProperties("products")
    private Category category;;

    @ManyToOne
    @JoinColumn(name = "id_quantity_type")
    @JsonIgnoreProperties("productList")
    private QuantityType quantityType;

    public Product(String productName, Float productPrice, String descriptions, Integer stock, String productImage, Brand brand, Category category, QuantityType quantityType) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.descriptions = descriptions;
        this.stock = stock;
        this.productImage = productImage;
        this.brand = brand;
        this.category = category;
        this.quantityType = quantityType;
    }
}
