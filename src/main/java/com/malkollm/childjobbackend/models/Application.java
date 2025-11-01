package com.malkollm.childjobbackend.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "application")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "child_id", nullable = false)
    private Child child;

    @ManyToOne
    @JoinColumn(name = "vacancy_id", nullable = false)
    private Vacancy vacancy;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status = ApplicationStatus.SUBMITTED;

    @Column(name = "applied_at", nullable = false, updatable = false)
    private LocalDateTime appliedAt;

    public enum ApplicationStatus {
        SUBMITTED, APPROVED, REJECTED, PENDING
    }

    // Constructors
    public Application() {}

    public Application(Child child, Vacancy vacancy) {
        this.child = child;
        this.vacancy = vacancy;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Child getChild() { return child; }
    public void setChild(Child child) { this.child = child; }

    public Vacancy getVacancy() { return vacancy; }
    public void setVacancy(Vacancy vacancy) { this.vacancy = vacancy; }

    public ApplicationStatus getStatus() { return status; }
    public void setStatus(ApplicationStatus status) { this.status = status; }

    public LocalDateTime getAppliedAt() { return appliedAt; }
    public void setAppliedAt(LocalDateTime appliedAt) { this.appliedAt = appliedAt; }
}