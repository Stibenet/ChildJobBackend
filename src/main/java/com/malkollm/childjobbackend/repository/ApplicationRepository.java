package com.malkollm.childjobbackend.repository;

import com.malkollm.childjobbackend.models.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByChildId(Long childId);
    List<Application> findByVacancyId(Long vacancyId);
    // Можете добавить методы для поиска по статусу и т.д.
}
