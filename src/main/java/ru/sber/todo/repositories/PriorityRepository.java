package ru.sber.todo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.todo.entities.priorities.Priority;

@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long> {

}
