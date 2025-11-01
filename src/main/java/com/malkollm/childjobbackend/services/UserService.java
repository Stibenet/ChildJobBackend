package com.malkollm.childjobbackend.services;

import com.malkollm.childjobbackend.models.ERole;
import com.malkollm.childjobbackend.models.Role;
import com.malkollm.childjobbackend.models.User;
import com.malkollm.childjobbackend.repository.RoleRepository;
import com.malkollm.childjobbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initRolesAndAdmin() {
        // Создаём роли, если их нет
        if (roleRepository.findByName(ERole.ROLE_ADMIN).isEmpty()) {
            roleRepository.save(new Role(ERole.ROLE_ADMIN));
        }
        if (roleRepository.findByName(ERole.ROLE_PARENT).isEmpty()) {
            roleRepository.save(new Role(ERole.ROLE_PARENT));
        }
        if (roleRepository.findByName(ERole.ROLE_GUEST).isEmpty()) {
            roleRepository.save(new Role(ERole.ROLE_GUEST));
        }
        if (roleRepository.findByName(ERole.ROLE_CHILD).isEmpty()) {
            roleRepository.save(new Role(ERole.ROLE_CHILD));
        }

        // Создаём администратора, если его нет
        if (userRepository.findByUsername("admin2").isEmpty()) {
            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN).get();
            User admin = new User("admin2", passwordEncoder.encode("password"));
            admin.setRoles(java.util.Set.of(adminRole));
            userRepository.save(admin);
        }
    }
}