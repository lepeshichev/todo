package ru.sber.todo.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.todo.entities.Task;
import ru.sber.todo.entities.priorities.Priority;
import ru.sber.todo.entities.statuses.Status;
import ru.sber.todo.services.PriorityService;
import ru.sber.todo.services.StatusService;
import ru.sber.todo.services.TaskService;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("tasks")
public class TaskController {
    private final TaskService taskService;
    private final StatusService statusService;
    private final PriorityService priorityService;

    @Autowired
    public TaskController(TaskService taskService, StatusService statusService, PriorityService priorityService) {
        this.taskService = taskService;
        this.statusService = statusService;
        this.priorityService = priorityService;
    }

    @PostMapping
    public ResponseEntity<?> addTask(@Valid @RequestBody Task task) {
        long taskId = taskService.save(task);
        log.info("Добавление задачи {}", task);
        if(taskId != -1) {
            return ResponseEntity.created(URI.create("tasks/" + taskId)).build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping
    public ResponseEntity<?> updateTask(@RequestBody Task task) {
        log.info("Обновление информации о задаче");
        boolean isUpdated = taskService.update(task); //Нужна какая-то проверка на false

        if (isUpdated) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable long taskId) {
        log.info("Удаление задачи по id: {}", taskId);
        boolean isDeleted = taskService.delete(taskId);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public List<Task> getTasks(){
        log.info("Получение всех задач пользователя");
        return taskService.findAll();
    }

    @GetMapping("/categories")
    public List<Task> getTasksByCategory(@RequestParam long categoryId) {
        log.info("Получение задач категории по id: {}", categoryId);
        return taskService.findByCategory(categoryId);
    }

    @GetMapping("/notify")
    public List<Task> getNotifyTasks() {
        log.info("Получение списка задач для уведомления пользователя");
        return taskService.isNotify();
    }

    @GetMapping("/statuses")
    public List<Status> getStatuses() {
        log.info("Получение всех статусов");
        return statusService.findAll();
    }

    @GetMapping("/priorities")
    public List<Priority> getPriorities() {
        log.info("Получение всех приоритетов");
        return priorityService.findAll();
    }

}
