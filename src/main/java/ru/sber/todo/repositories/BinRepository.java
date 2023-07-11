package ru.sber.todo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.todo.entities.Bin;

@Repository
public interface BinRepository extends JpaRepository<Bin, Long> {
}
