package com.malkollm.childjobbackend.controllers;

import com.malkollm.childjobbackend.models.Vacancy;
import com.malkollm.childjobbackend.repository.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/vacancies")
public class VacancyController {

    @Autowired
    private VacancyRepository vacancyRepository;

    @PostMapping
    public ResponseEntity<Vacancy> createVacancy(@RequestBody Vacancy vacancy) {
        Vacancy savedVacancy = vacancyRepository.save(vacancy);
        return ResponseEntity.ok(savedVacancy);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vacancy> getVacancyById(@PathVariable Long id) {
        return vacancyRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/getAllActiveVacancies")
    public ResponseEntity<List<Vacancy>> getAllActiveVacancies() {
        List<Vacancy> vacancies = vacancyRepository.findByIsActiveTrue();
        return ResponseEntity.ok(vacancies);
    }

    // Получить вакансии, подходящие по возрасту
    @GetMapping("/for-age/{age}")
    public ResponseEntity<List<Vacancy>> getVacanciesForAge(@PathVariable Integer age) {
        List<Vacancy> vacancies = vacancyRepository.findByMinAgeLessThanEqualAndMaxAgeGreaterThanEqual(age, age);
        return ResponseEntity.ok(vacancies);
    }

    @PutMapping("/{id}/toggle-active")
    public ResponseEntity<Vacancy> toggleVacancyActive(@PathVariable Long id) {
        return vacancyRepository.findById(id).map(vacancy -> {
            vacancy.setIsActive(!vacancy.getIsActive());
            Vacancy updated = vacancyRepository.save(vacancy);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }
}
