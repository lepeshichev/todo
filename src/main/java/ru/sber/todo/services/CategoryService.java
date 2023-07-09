package ru.sber.todo.services;

import ru.sber.todo.entities.Category;

import java.util.List;

public interface CategoryService {
    long save(Category category);
    boolean update(Category category);
    boolean delete(long idCategory);
    List<Category> findAll();
    boolean isExistByCategory(long idCategory);
}
