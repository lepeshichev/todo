package ru.sber.todo.services;


import ru.sber.todo.entities.Task;

import java.util.List;

public interface TaskService {

    Long createTask(Task task);

    boolean updateTask(Task task);

    boolean deleteTaskById(long taskId);

    List<Task> isNotify(long userId);

    List<Task> findAll(long userId);

    List<Task> findAllByCategoryId(long categoryId);
}
