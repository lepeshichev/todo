package ru.sber.todo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.todo.entities.statuses.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {

}
