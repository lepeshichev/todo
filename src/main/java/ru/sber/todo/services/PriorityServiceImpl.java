package ru.sber.todo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.todo.entities.priorities.Priority;
import ru.sber.todo.repositories.PriorityRepository;

import java.util.List;

@Service
public class PriorityServiceImpl implements PriorityService{
    private final PriorityRepository priorityRepository;

    @Autowired
    public PriorityServiceImpl(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }

    @Override
    public List<Priority> findAll() {
        return priorityRepository.findAll();
    }
}
