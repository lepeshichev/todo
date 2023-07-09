package ru.sber.todo.services;

import ru.sber.todo.entities.statuses.Status;

import java.util.List;

public interface StatusService {
    List<Status> findAll();
}
