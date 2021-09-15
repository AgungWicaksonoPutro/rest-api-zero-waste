package com.enigma.zerowaste.repository;

import com.enigma.zerowaste.entity.ERole;
import com.enigma.zerowaste.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    Optional<Role> findByName(ERole name);
}
