package com.enigma.zerowaste.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;


@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@NoArgsConstructor
@Entity
@Table(name = "mst_user")
public class Customer extends Auditable<String> {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "customer_id")
    private String id;
    @Column(name = "customer_name")
    private String customerName;
    @Column(name = "phone_number")
    private String phone;
    @Column(name = "username")
    private String userName;
    @Email
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Column(name = "email_verified")
    private Boolean emailVerfied;
    @Column(name = "is_deleted")
    private Boolean isDeleted = false;
    private Boolean newMember = true;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.PERSIST)
    @JsonIgnoreProperties("customer")
    private List<Address> addresses = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.PERSIST)
    @JsonIgnore
    private List<ShoppingBag> shoppingBags = new ArrayList<>();

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Order> orders;


    public Customer(String customerName, String phone, String userName, String email, String encode) {
        this.customerName = customerName;
        this.phone = phone;
        this.userName = userName;
        this.email = email;
        this.password = encode;
    }

    public Customer(String id, String customerName, String phone, String userName, @Email String email, String password, Boolean emailVerfied, Boolean isDeleted, List<Address> addresses, Set<Role> roles) {
        this.id = id;
        this.customerName = customerName;
        this.phone = phone;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.emailVerfied = emailVerfied;
        this.isDeleted = isDeleted;
        this.addresses = addresses;
        this.roles = roles;
    }

    //role null
    public Customer(String customerName, String phone, String userName, @Email String email, String password, Boolean emailVerfied, Boolean isDeleted, List<Address> addresses, Set<Role> roles) {
        this.customerName = customerName;
        this.phone = phone;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.emailVerfied = emailVerfied;
        this.isDeleted = isDeleted;
        this.addresses = addresses;
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", customerName='" + customerName + '\'' +
                ", phone='" + phone + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", emailVerfied=" + emailVerfied +
                ", isDeleted=" + isDeleted +
                ", addresses=" + addresses +
                ", roles=" + roles +
                ", shoppingBags=" + shoppingBags +
                ", orders=" + orders +
                '}';
    }
}
