package com.eventtickets.datatier.controllers;

import com.eventtickets.datatier.model.Category;
import com.eventtickets.datatier.persistence.CategoryRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor

public class CategoryController {
    @NonNull
    private CategoryRepository categoryRepository;

    @PostMapping
    public Category createCategory(@RequestBody Category category) {
        return categoryRepository.save(category);
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @GetMapping("/byName")
    public ResponseEntity<Category> findByName(@RequestParam String name)
    {
        return  ResponseEntity.of(categoryRepository.findByName(name));
    }
}
