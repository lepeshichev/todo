package ru.sber.todo.services;
import org.springframework.stereotype.Service;
import ru.sber.todo.entities.Category;
import ru.sber.todo.entities.Task;
import ru.sber.todo.repositories.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;

import java.util.ArrayList;


@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final CategoryService categoryService;

    public TaskServiceImpl(TaskRepository taskRepository, CategoryService categoryService) {
        this.taskRepository = taskRepository;
        this.categoryService = categoryService;
    }

    @Override
    public Long createTask(Task task) {
        return taskRepository.save(task).getId();
    }

    @Override
    public List<Task> findAll(long userId) {
        List<Category> categories = categoryService.findAllByUserId(userId);
        if (categories.isEmpty()) {
            return List.of();
        } else {
            List<Task> taskList = new ArrayList<>();
            for (Category category: categories) {
                taskList.addAll(findAllByCategoryId(category.getId()));
            }
            return taskList;
        }
    }

    @Override
    public List<Task> isNotify(long userId) {
        return categoryService.findAllByUserId(userId)
                .stream()
                .flatMap(category ->
                        taskRepository.findByCategory_Id(category.getId())
                                .stream()
                                .filter(TaskServiceImpl::isNotify))
                .toList();
    }

    private static boolean isNotify(Task task) {
        LocalDateTime nowTime = LocalDateTime.now();
        LocalDateTime taskEndTime = task.getTaskEndTime();
        return nowTime.isAfter(taskEndTime);
    }

    @Override
    public List<Task> findAllByCategoryId(long categoryId) {
        return taskRepository.findByCategory_Id(categoryId)
                .stream()
                .toList();
    }

    @Override
    public boolean updateTask(Task task) {
        if (taskRepository.existsById(task.getId())) {
            taskRepository.save(task);
            return true;
        }

        return false;
    }

    @Override
    public boolean deleteTaskById(long taskId) {
        taskRepository.deleteById(taskId);
        return true;
    }
}
