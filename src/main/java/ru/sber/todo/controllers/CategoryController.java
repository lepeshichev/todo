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
        log.info("Добавление новой категории {}", category);
        long idCategory = categoryService.createCategory(category);
        return ResponseEntity.created(URI.create("/categories/" + idCategory)).build();
    }

    @PutMapping
    public ResponseEntity<?> updateCategory(@Valid @RequestBody Category category) {
        log.info("Обновление информации о категории {}", category);
        boolean isUpdate = categoryService.updateCategory(category);
        if (isUpdate) {
            log.info("Обновление информации о категории успешно");
            return ResponseEntity.ok().build();
        } else {
            log.error("Обновление информации о категории провалено");
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCategory(@RequestParam long idCategory) {
        log.info("Удаление категории по id{}", idCategory);
        boolean isDelete = categoryService.deleteCategoryById(idCategory);
        if (isDelete) {
            log.info("Удаление категории успешно");
            return ResponseEntity.noContent().build();
        } else {
            log.error("Удаление категории провалено");
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Category>> getCategories(@PathVariable long userId) {
        log.info("Получение категорий по user id");
        return ResponseEntity.ok().body(categoryService.findAllByUserId(userId));
    }

}
