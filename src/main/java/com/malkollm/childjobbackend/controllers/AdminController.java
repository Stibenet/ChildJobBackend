package com.malkollm.childjobbackend.controllers;

import com.malkollm.childjobbackend.models.Vacancy;
import com.malkollm.childjobbackend.repository.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private VacancyRepository vacancyRepository; // Добавим репозиторий

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminDashboard() {
        return "Добро пожаловать в админ-панель!";
    }

    // Новый метод для получения всех вакансий админом
    @GetMapping("/vacancies")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Vacancy>> getAllVacancies() {
        List<Vacancy> vacancies = vacancyRepository.findAll(); // Получаем все вакансии
        return ResponseEntity.ok(vacancies);
    }
}
