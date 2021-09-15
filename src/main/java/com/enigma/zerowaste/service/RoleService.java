package com.enigma.zerowaste.service;

import com.enigma.zerowaste.entity.ERole;
import com.enigma.zerowaste.entity.Role;

import java.util.Optional;

public interface RoleService {
    public Optional<Role> findByName(ERole name);
}
