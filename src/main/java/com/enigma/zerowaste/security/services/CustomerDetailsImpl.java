package com.enigma.zerowaste.security.services;

import com.enigma.zerowaste.entity.Customer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@AllArgsConstructor @NoArgsConstructor
public class CustomerDetailsImpl implements UserDetails {

    private String id;
    private String customerName;
    private String phone;
    private String userName;
    private String email;
    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public static CustomerDetailsImpl build(Customer customer){
        List<GrantedAuthority> authorities = customer.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
        return new CustomerDetailsImpl(
                customer.getId(),
                customer.getCustomerName(),
                customer.getPhone(),
                customer.getUserName(),
                customer.getEmail(),
                customer.getPassword(),
                authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public String getPassword(){
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerDetailsImpl that = (CustomerDetailsImpl) o;
        return Objects.equals(id, that.id) && Objects.equals(customerName, that.customerName) && Objects.equals(phone, that.phone) && Objects.equals(userName, that.userName) && Objects.equals(email, that.email) && Objects.equals(password, that.password) && Objects.equals(authorities, that.authorities);
    }
}
