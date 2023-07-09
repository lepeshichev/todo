package ru.sber.todo.services;


import ru.sber.todo.entities.Task;

import java.util.List;

public interface TaskService {

    long save(Task task);

    boolean update(Task task);

    boolean delete(long taskId);

    List<Task> findAll();

    List<Task> findByCategory(long categoryId);

    List<Task> isNotify();
}
