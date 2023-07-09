package ru.sber.todo.services;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.todo.entities.Category;
import ru.sber.todo.entities.Task;
import ru.sber.todo.repositories.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final CategoryService categoryService;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, CategoryService categoryService) {
        this.taskRepository = taskRepository;
        this.categoryService = categoryService;
    }

    @Override
    public long save(Task task) {
        boolean isCategoryExist = categoryService.isExistByCategory(task.getCategory().getId());
        if (isCategoryExist) {
            return taskRepository.save(task).getId();
        }
        return -1L;
    }

    @Override
    public boolean update(Task task) {
        boolean isTaskExist = taskRepository.existsById(task.getId());
        boolean isCategoryRight = categoryService.isExistByCategory(task.getCategory().getId());
        if (isTaskExist && isCategoryRight) {
            taskRepository.save(task);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(long taskId) {
        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isPresent() &&
                categoryService.isExistByCategory(task.get().getCategory().getId())) {
            taskRepository.deleteById(taskId);
            return true;
        }
        return false;
    }

    @Override
    public List<Task> findAll() {
        List<Category> categories = categoryService.findAll();
        return categories.stream()
                .flatMap(category -> findByCategory(category.getId())
                        .stream())
                .toList();
    }

    @Override
    public List<Task> findByCategory(long categoryId) {
        List<Category> categories = categoryService.findAll();
        return categories.stream()
                .filter(category->category.getId() == categoryId)
                .flatMap(category -> findByCategory(category.getId())
                        .stream())
                .toList();
    }

    @Override
    public List<Task> isNotify() {
        var now = LocalDateTime.now();
        // todo: как-то вычислить время
        return null;
    }
}
