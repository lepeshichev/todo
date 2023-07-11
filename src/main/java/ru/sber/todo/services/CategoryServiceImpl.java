package ru.sber.todo.services;

import org.springframework.stereotype.Service;
import ru.sber.todo.entities.Category;
import ru.sber.todo.repositories.CategoryRepository;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Long createCategory(Category category) {
        return categoryRepository.save(category).getId();
    }

    @Override
    public List<Category> findAllByUserId(long userId) {
        return categoryRepository.findAllByUser_Id(userId);
    }

    @Override
    public boolean updateCategory(Category category) {
        if (categoryRepository.existsById(category.getId())) {
            categoryRepository.save(category);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteCategoryById(long categoryId) {
        if (categoryRepository.existsById(categoryId)) {
            categoryRepository.deleteById(categoryId);
            return true;
        }
        return false;
    }
}
