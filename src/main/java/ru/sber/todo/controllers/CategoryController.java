package ru.sber.todo.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.todo.entities.Category;
import ru.sber.todo.services.CategoryService;

import java.net.URI;
import java.util.List;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<?> addCategory(@Valid @RequestBody Category category) {
        log.info("Добавление категории {}", category);
        long idCategory = categoryService.save(category);
        return ResponseEntity.created(URI.create("/categories/" + idCategory)).build();
    }

    @GetMapping()
    public ResponseEntity<List<Category>> getCategories() {
        log.info("Получение всех категорий");
        return ResponseEntity.ok().body(categoryService.findAll());
    }

    @PutMapping
    public ResponseEntity<?> updateCategory(@Valid @RequestBody Category category) {
        log.info("Обновление информации о категории {}", category);
        boolean isUpdate = categoryService.update(category);
        if (isUpdate) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCategory(@RequestParam long idCategory) {
        log.info("Удаление категории по id{}", idCategory);
        boolean isDelete = categoryService.delete(idCategory);
        if (isDelete) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
