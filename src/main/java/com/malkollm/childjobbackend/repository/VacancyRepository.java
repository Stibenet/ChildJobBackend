package com.malkollm.childjobbackend.repository;

import com.malkollm.childjobbackend.models.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
    List<Vacancy> findByIsActiveTrue(); // Только активные вакансии
    List<Vacancy> findByMinAgeLessThanEqualAndMaxAgeGreaterThanEqual(Integer age, Integer age2); // Вакансии по возрасту
}