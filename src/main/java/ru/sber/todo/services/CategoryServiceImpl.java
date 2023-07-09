package ru.sber.todo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.sber.todo.entities.Category;
import ru.sber.todo.entities.User;
import ru.sber.todo.repositories.CategoryRepository;
import ru.sber.todo.security.services.UserDetailsImpl;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public long save(Category category) {
        long userId = getUserIdFromSecurityContext();
        category.setUser(new User(userId));
        return categoryRepository.save(category).getId();
    }

    @Override
    public boolean update(Category category) {
        long userId = getUserIdFromSecurityContext();
        if (categoryRepository.isExistsByIdAndUserId(category.getId(),
                userId)){
            category.setUser(new User(userId));
            categoryRepository.save(category);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(long idCategory) {
        long userId = getUserIdFromSecurityContext();
        if (categoryRepository.isExistsByIdAndUserId(idCategory, userId)) {
            categoryRepository.deleteById(idCategory);
            return true;
        }
        return false;
    }

    private long getUserIdFromSecurityContext() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetailsImpl) {
            return ((UserDetailsImpl)principal).getId();
        } else {
            throw new RuntimeException("Пользователь не существует");
        }
    }
    @Override
    public List<Category> findAll() {
        long userId = getUserIdFromSecurityContext();
        return categoryRepository.findCategoryByUserId(userId);
    }

    @Override
    public boolean isExistByCategory(long idCategory) {
        long userId = getUserIdFromSecurityContext();
        return categoryRepository.isExistsByIdAndUserId(idCategory, userId);
    }
}
