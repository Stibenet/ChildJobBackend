package com.malkollm.childjobbackend.models.request;

import jakarta.validation.constraints.Size;
import java.util.Set;

public class UserUpdateRequest {
    @Size(min = 3, max = 20)
    private String username;

    // Пароль может быть пустым при обновлении, если не меняем
    @Size(min = 6, max = 40)
    private String password;

    private Set<String> roles;

    // Constructors
    public UserUpdateRequest() {}
    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Set<String> getRoles() { return roles; }
    public void setRoles(Set<String> roles) { this.roles = roles; }
}
