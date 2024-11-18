package raingor.ru.taskmanagementsystems.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import raingor.ru.taskmanagementsystems.dto.TaskDTO;
import raingor.ru.taskmanagementsystems.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Tag(name = "Задачи")
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public List<TaskDTO> getTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping
    public List<TaskDTO> getTasks(@RequestParam(value = "offset") Integer offset,
                                  @RequestParam(value = "limit") Integer limit) {
        return taskService.getAllTasks(PageRequest.of(offset, limit));
    }

    @PostMapping
    @Operation(summary = "Доступен только авторизованным пользователям с ролью ADMIN")
    @PreAuthorize("hasRole('ADMIN')")
    public HttpStatus addTask(@RequestBody TaskDTO taskDTO) {
        taskService.addTask(taskDTO);

        return HttpStatus.CREATED;
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Доступен только авторизованным пользователям с ролью ADMIN")
    @PreAuthorize("hasRole('ADMIN')")
    public HttpStatus updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        taskService.updateTask(id, taskDTO);

        return HttpStatus.NO_CONTENT;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Доступен только авторизованным пользователям с ролью ADMIN")
    @PreAuthorize("hasRole('ADMIN')")
    public HttpStatus deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);

        return HttpStatus.NO_CONTENT;
    }
}
