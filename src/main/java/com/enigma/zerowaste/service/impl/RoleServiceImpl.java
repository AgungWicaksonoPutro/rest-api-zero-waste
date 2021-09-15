package com.enigma.zerowaste.service.impl;

import com.enigma.zerowaste.entity.ERole;
import com.enigma.zerowaste.entity.Role;
import com.enigma.zerowaste.repository.RoleRepository;
import com.enigma.zerowaste.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepository roleRepository;

    @Override
    public Optional<Role> findByName(ERole name) {
        return roleRepository.findByName(name);
    }
}
