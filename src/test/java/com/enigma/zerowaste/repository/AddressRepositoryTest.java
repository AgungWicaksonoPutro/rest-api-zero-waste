//package com.enigma.zerowaste.repository;
//
//import com.enigma.zerowaste.entity.Address;
//import com.enigma.zerowaste.entity.Customer;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.concurrent.atomic.AtomicInteger;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//class AddressRepositoryTest {
//
//    @Autowired
//    private AddressRepository addressRepository;
//
//    private Customer customer = new Customer();
//
//    @BeforeEach
//    void initUseCase() {
//        List<Address> addresses = Arrays.asList(
//                new Address("id", "name", "234435", "jalan", customer, false)
//        );
//        addressRepository.saveAll(addresses);
//    }
//
//    @AfterEach
//    public void destroyAll() {
//        addressRepository.deleteAll();
//    }
//
//    @Test
//    void saveAll_Succes(){
//        List<Address> addresses = Arrays.asList(
//                new Address("id", "name", "234435", "jalan", customer, false),
//        new Address("id2", "name2", "2344352", "jalan2", customer, false)
//        );
//        Iterable<Address> allAddress = addressRepository.saveAll(addresses);
//
//        AtomicInteger validIdFound = new AtomicInteger();
//        allAddress.forEach(address -> {
//            if(address.getId()>0){
//                validIdFound.getAndIncrement();
//            }
//        });
//
//        assertThat(validIdFound.intValue()).isEqualTo(3);
//    }
//
//
//    @Test
//    void findAll() {
//        List<Address> addresses = addressRepository.findAll();
//        assertThat
//    }
//}