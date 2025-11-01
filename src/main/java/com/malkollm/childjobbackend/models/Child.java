package com.malkollm.childjobbackend.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "child")
public class Child {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer age;

    private String parentContact;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Constructors
    public Child() {}

    public Child(String name, Integer age, String parentContact) {
        this.name = name;
        this.age = age;
        this.parentContact = parentContact;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public String getParentContact() { return parentContact; }
    public void setParentContact(String parentContact) { this.parentContact = parentContact; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}