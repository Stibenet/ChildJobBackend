package com.malkollm.childjobbackend.repository;

import com.malkollm.childjobbackend.models.ERole;
import com.malkollm.childjobbackend.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
