package ru.sber.todo.services;

import ru.sber.todo.entities.Category;

import java.util.List;

public interface CategoryService {
    Long createCategory(Category category);

    List<Category> findAllByUserId(long userId);

    boolean updateCategory(Category category);

    boolean deleteCategoryById(long categoryId);
}
