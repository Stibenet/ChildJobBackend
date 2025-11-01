package com.malkollm.childjobbackend.repository;

import com.malkollm.childjobbackend.models.Child;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChildRepository extends JpaRepository<Child, Long> {
}
