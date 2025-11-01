package com.malkollm.childjobbackend.controllers;

import com.malkollm.childjobbackend.models.Child;
import com.malkollm.childjobbackend.repository.ChildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/children")
public class ChildController {

    @Autowired
    private ChildRepository childRepository;

    @PostMapping
    public ResponseEntity<Child> createChild(@RequestBody Child child) {
        Child savedChild = childRepository.save(child);
        return ResponseEntity.ok(savedChild);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Child> getChildById(@PathVariable Long id) {
        return childRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Child>> getAllChildren() {
        List<Child> children = childRepository.findAll();
        return ResponseEntity.ok(children);
    }
}
