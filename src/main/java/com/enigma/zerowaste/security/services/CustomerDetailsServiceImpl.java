package com.enigma.zerowaste.security.services;

import com.enigma.zerowaste.entity.Customer;
import com.enigma.zerowaste.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerDetailsServiceImpl implements UserDetailsService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByUserName(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found with username"+username));
        return CustomerDetailsImpl.build(customer);
    }
}
