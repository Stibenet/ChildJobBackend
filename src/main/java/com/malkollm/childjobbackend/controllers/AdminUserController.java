package com.malkollm.childjobbackend.controllers;

import com.malkollm.childjobbackend.models.ERole;
import com.malkollm.childjobbackend.models.Role;
import com.malkollm.childjobbackend.models.SignupRequest;
import com.malkollm.childjobbackend.models.User;
import com.malkollm.childjobbackend.models.dto.UserInfoResponse;
import com.malkollm.childjobbackend.models.request.UserUpdateRequest;
import com.malkollm.childjobbackend.repository.RoleRepository;
import com.malkollm.childjobbackend.repository.UserRepository;
import com.malkollm.childjobbackend.repository.RoleRepository;
import com.malkollm.childjobbackend.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/admin/users")
@PreAuthorize("hasRole('ADMIN')") // Общий доступ для этого контроллера
public class AdminUserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    // GET /api/admin/users - получить всех пользователей
    @GetMapping
    public ResponseEntity<List<UserInfoResponse>> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserInfoResponse> userInfos = users.stream()
                .map(user -> new UserInfoResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getRoles().stream().map(r -> r.getName().name()).collect(Collectors.toSet())
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(userInfos);
    }

    // GET /api/admin/users/{id} - получить пользователя по ID
    @GetMapping("/{id}")
    public ResponseEntity<UserInfoResponse> getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> new UserInfoResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getRoles().stream().map(r -> r.getName().name()).collect(Collectors.toSet())
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/admin/users - создать нового пользователя
    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.findByUsername(signUpRequest.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Ошибка: Имя пользователя уже занято!");
        }

        User user = new User(signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null || strRoles.isEmpty()) {
            Role userRole = roleRepository.findByName(ERole.ROLE_GUEST)
                    .orElseThrow(() -> new RuntimeException("Ошибка: Роль не найдена."));
            roles.add(userRole);
        } else {
            strRoles.forEach(roleStr -> {
                ERole roleEnum = ERole.valueOf("ROLE_" + roleStr.toUpperCase());
                Role role = roleRepository.findByName(roleEnum)
                        .orElseThrow(() -> new RuntimeException("Ошибка: Роль " + roleEnum + " не найдена."));
                roles.add(role);
            });
        }

        user.setRoles(roles);
        User savedUser = userRepository.save(user);

        UserInfoResponse response = new UserInfoResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getRoles().stream().map(r -> r.getName().name()).collect(Collectors.toSet())
        );
        return ResponseEntity.ok(response);
    }

    // PUT /api/admin/users/{id} - обновить пользователя (включая роли)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Обновляем имя (если нужно, можно добавить проверку на уникальность, кроме текущего пользователя)
            user.setUsername(userUpdateRequest.getUsername());

            // Обновляем пароль, если он передан в запросе
            if (userUpdateRequest.getPassword() != null && !userUpdateRequest.getPassword().isEmpty()) {
                user.setPassword(encoder.encode(userUpdateRequest.getPassword()));
            }
            // Если password не передан или пустой, оставляем старый пароль

            // Обновляем роли
            Set<String> strRoles = userUpdateRequest.getRoles();
            // Создаём новое множество ролей
            Set<Role> newRoles = new HashSet<>();
            if (strRoles != null && !strRoles.isEmpty()) {
                // Используем стандартный цикл for вместо forEach
                for (String roleStr : strRoles) {
                    ERole roleEnum = ERole.valueOf("ROLE_" + roleStr.toUpperCase());
                    // Используем roleRepository напрямую внутри цикла
                    Role role = roleRepository.findByName(roleEnum)
                            .orElseThrow(() -> new RuntimeException("Ошибка: Роль " + roleEnum + " не найдена."));
                    newRoles.add(role);
                }
            } else {
                // Если роли не переданы, оставляем старые
                newRoles = user.getRoles();
            }
            user.setRoles(newRoles); // Устанавливаем новое множество ролей

            User updatedUser = userRepository.save(user);

            UserInfoResponse response = new UserInfoResponse(
                    updatedUser.getId(),
                    updatedUser.getUsername(),
                    updatedUser.getRoles().stream().map(r -> r.getName().name()).collect(Collectors.toSet())
            );
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    // DELETE /api/admin/users/{id} - удалить пользователя
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.ok().build(); // 200 OK
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
}