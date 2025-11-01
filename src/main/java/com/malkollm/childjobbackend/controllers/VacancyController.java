package com.malkollm.childjobbackend.controllers;

import com.malkollm.childjobbackend.models.Vacancy;
import com.malkollm.childjobbackend.repository.VacancyRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/vacancies")
public class VacancyController {

    @Autowired
    private VacancyRepository vacancyRepository;

    // GET /api/vacancies - доступно всем
    @GetMapping
    public ResponseEntity<List<Vacancy>> getAllActiveVacancies() {
        List<Vacancy> vacancies = vacancyRepository.findByIsActiveTrue();
        return ResponseEntity.ok(vacancies);
    }

    // GET /api/vacancies/{id} - доступно всем
    @GetMapping("/{id}")
    public ResponseEntity<Vacancy> getVacancyById(@PathVariable Long id) {
        Optional<Vacancy> vacancy = vacancyRepository.findById(id);
        if (vacancy.isPresent()) {
            return ResponseEntity.ok(vacancy.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST /api/vacancies - только для админов
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Vacancy> createVacancy(@Valid @RequestBody Vacancy vacancy) {
        Vacancy savedVacancy = vacancyRepository.save(vacancy);
        return ResponseEntity.ok(savedVacancy);
    }

    // PUT /api/vacancies/{id} - только для админов
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Vacancy> updateVacancy(@PathVariable Long id, @Valid @RequestBody Vacancy vacancyDetails) {
        Optional<Vacancy> vacancyOptional = vacancyRepository.findById(id);
        if (vacancyOptional.isPresent()) {
            Vacancy vacancy = vacancyOptional.get();
            // Обновляем поля
            vacancy.setTitle(vacancyDetails.getTitle());
            vacancy.setDescription(vacancyDetails.getDescription());
            vacancy.setMinAge(vacancyDetails.getMinAge());
            vacancy.setMaxAge(vacancyDetails.getMaxAge());
            vacancy.setLocation(vacancyDetails.getLocation());
            vacancy.setEmployerContact(vacancyDetails.getEmployerContact());
            // Не обновляем is_active и created_at здесь, если не нужно
            Vacancy updatedVacancy = vacancyRepository.save(vacancy);
            return ResponseEntity.ok(updatedVacancy);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/vacancies/{id} - только для админов
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteVacancy(@PathVariable Long id) {
        if (vacancyRepository.existsById(id)) {
            vacancyRepository.deleteById(id);
            return ResponseEntity.ok().build(); // 200 OK
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    // PUT /api/vacancies/{id}/toggle-active - только для админов
    @PutMapping("/{id}/toggle-active")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Vacancy> toggleVacancyActive(@PathVariable Long id) {
        return vacancyRepository.findById(id).map(vacancy -> {
            vacancy.setIsActive(!vacancy.getIsActive());
            Vacancy updated = vacancyRepository.save(vacancy);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }
}