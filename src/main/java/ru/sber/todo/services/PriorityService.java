package ru.sber.todo.services;

import ru.sber.todo.entities.priorities.Priority;

import java.util.List;

public interface PriorityService {
    List<Priority> findAll();
}
