package com.malkollm.childjobbackend.controllers;

import com.malkollm.childjobbackend.models.Application;
import com.malkollm.childjobbackend.models.Child;
import com.malkollm.childjobbackend.models.Vacancy;
import com.malkollm.childjobbackend.repository.ApplicationRepository;
import com.malkollm.childjobbackend.repository.ChildRepository;
import com.malkollm.childjobbackend.repository.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ChildRepository childRepository;

    @Autowired
    private VacancyRepository vacancyRepository;

    @PostMapping
    public ResponseEntity<Application> createApplication(@RequestBody ApplicationRequest request) {
        // Проверить существование ребёнка и вакансии
        if (!childRepository.existsById(request.getChildId()) || !vacancyRepository.existsById(request.getVacancyId())) {
            return ResponseEntity.badRequest().build(); // Или более конкретный ответ
        }

        Child child = childRepository.findById(request.getChildId()).get();
        Vacancy vacancy = vacancyRepository.findById(request.getVacancyId()).get();

        Application application = new Application(child, vacancy);
        application.setStatus(Application.ApplicationStatus.SUBMITTED); // По умолчанию

        Application savedApplication = applicationRepository.save(application);
        return ResponseEntity.ok(savedApplication);
    }

    @GetMapping("/child/{childId}")
    public ResponseEntity<List<Application>> getApplicationsByChild(@PathVariable Long childId) {
        List<Application> applications = applicationRepository.findByChildId(childId);
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/vacancy/{vacancyId}")
    public ResponseEntity<List<Application>> getApplicationsByVacancy(@PathVariable Long vacancyId) {
        List<Application> applications = applicationRepository.findByVacancyId(vacancyId);
        return ResponseEntity.ok(applications);
    }

    // DTO для запроса создания
    public static class ApplicationRequest {
        private Long childId;
        private Long vacancyId;

        // Getters and Setters
        public Long getChildId() { return childId; }
        public void setChildId(Long childId) { this.childId = childId; }

        public Long getVacancyId() { return vacancyId; }
        public void setVacancyId(Long vacancyId) { this.vacancyId = vacancyId; }
    }
}
