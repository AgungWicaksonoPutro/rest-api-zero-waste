package com.enigma.zerowaste.controller;

import com.enigma.zerowaste.constant.ResponseMessage;
import com.enigma.zerowaste.entity.Customer;
import com.enigma.zerowaste.entity.ERole;
import com.enigma.zerowaste.entity.Role;
import com.enigma.zerowaste.security.jwt.AuthTokenFilter;
import com.enigma.zerowaste.security.jwt.JwtUtils;
import com.enigma.zerowaste.security.payload.request.LoginRequest;
import com.enigma.zerowaste.security.payload.request.SignupRequest;
import com.enigma.zerowaste.security.payload.response.JwtResponse;
import com.enigma.zerowaste.security.payload.response.MessageResponse;
import com.enigma.zerowaste.security.services.CustomerDetailsImpl;
import com.enigma.zerowaste.service.impl.CustomerServiceImpl;
import com.enigma.zerowaste.service.impl.RoleServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomerServiceImpl customerService;

    @Autowired
    RoleServiceImpl roleService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getUserPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        CustomerDetailsImpl customerDetails = (CustomerDetailsImpl) authentication.getPrincipal();
        List<String> roles = customerDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt, customerDetails.getId(),
                customerDetails.getCustomerName(),
                customerDetails.getPhone(),
                customerDetails.getUsername(),
                customerDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (customerService.userNameExist(signupRequest.getUserName())) {
            String message = ResponseMessage.ERROR_MESSAGE_USERNAME;
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(message));
        }
        if (customerService.emailExist(signupRequest.getEmail())) {
            String message = ResponseMessage.ERROR_MESSAGE_EMAIL;
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(message));
        }

        //create new Customer
        Customer customer = new Customer(signupRequest.getCustomerName(),
                signupRequest.getPhone(),
                signupRequest.getUserName(),
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()));

        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();
        String message = ResponseMessage.ERROR_MESSAGE_ROLE;
        if (strRoles == null) {
            Role userRole = roleService.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException(message));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleService.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException(message));
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleService.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException(message));
                        roles.add(userRole);
                }
            });
        }
        customer.setRoles(roles);
        customerService.saveCustomer(customer);
        String messageSucces = ResponseMessage.SUCCES_MESSAGE_ROLE;
        return ResponseEntity.ok(new MessageResponse(messageSucces));
    }
}
