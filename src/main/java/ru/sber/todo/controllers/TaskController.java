package ru.sber.todo.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.todo.entities.*;
import ru.sber.todo.entities.priorities.Priority;
import ru.sber.todo.entities.statuses.Status;
import ru.sber.todo.services.PriorityService;
import ru.sber.todo.services.StatusService;
import ru.sber.todo.services.TaskService;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("tasks")
public class TaskController {

    private final TaskService taskService;
    private final StatusService statusService;
    private final PriorityService priorityService;

    @Autowired
    public TaskController(TaskService taskService, StatusService statusService,
                          PriorityService priorityService) {
        this.taskService = taskService;
        this.statusService = statusService;
        this.priorityService = priorityService;
    }

    @PostMapping
    public ResponseEntity<?> addTask(@Valid @RequestBody Task task) {
        long taskId = taskService.createTask(task);
        log.info("Добавление задачи {}", task);
        if(taskId != -1) {
            return ResponseEntity.created(URI.create("tasks/" + taskId)).build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping
    public ResponseEntity<?> updateTask(@Valid @RequestBody Task task) {
        boolean isUpdated = taskService.updateTask(task);
        log.info("Обновление информации о задаче");
        if (isUpdated) {
            log.info("Успешное обновление информации о задаче");
            return ResponseEntity.ok().build();
        } else {
            log.error("Не удалось обновить информацию о задачу");
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable long taskId) {
        boolean isDeleted = taskService.deleteTaskById(taskId);
        if (isDeleted) {
            log.info("Успешное удаление задачи по id");
            return ResponseEntity.noContent().build();
        } else {
            log.error("Не удалось удалить задачу по id");
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public List<Task> getTasks(@RequestParam long userId) {
        log.info("Получение всех задач пользователя");
        return taskService.findAll(userId);
    }

    @GetMapping("/categories")
    public List<Task> getTasksByCategory(@RequestParam long categoryId) {
        log.info("Получение задач категории по id: {}", categoryId);
        return taskService.findAllByCategoryId(categoryId);
    }

    @GetMapping("/notifications")
    public List<Task> getAllTasksForNotification(@RequestParam long userId) {
        log.info("Получение списка задач для уведомления пользователя");
        return taskService.isNotify(userId);
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
