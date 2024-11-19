package raingor.ru.taskmanagementsystems.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import raingor.ru.taskmanagementsystems.domain.Priority;
import raingor.ru.taskmanagementsystems.domain.Status;
import raingor.ru.taskmanagementsystems.domain.Task;
import raingor.ru.taskmanagementsystems.domain.User;
import raingor.ru.taskmanagementsystems.dto.StatusTaskDTO;
import raingor.ru.taskmanagementsystems.dto.TaskDTO;
import raingor.ru.taskmanagementsystems.exceptions.TaskNotFoundException;
import raingor.ru.taskmanagementsystems.exceptions.UserNotFoundException;
import raingor.ru.taskmanagementsystems.repository.TaskRepository;
import raingor.ru.taskmanagementsystems.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public List<TaskDTO> getAllTasks(Pageable pageRequest) {
        if (pageRequest == null) {
            return taskRepository.findAll().stream().map(this::taskToDTO).toList();
        }
        return taskRepository.findAll(pageRequest).stream().map(this::taskToDTO).toList();
    }

    public void addTask(TaskDTO taskDTO) {
        taskRepository.save(toEntity(taskDTO));
    }

    public void updateTask(Long id, TaskDTO taskDTO) {
        Task task = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);

        Optional.ofNullable(taskDTO.title()).ifPresent(task::setTitle);
        Optional.ofNullable(taskDTO.description()).ifPresent(task::setDescription);
        Optional.ofNullable(taskDTO.status())
                .map(Status::valueOf)
                .ifPresent(task::setStatus);
        Optional.ofNullable(taskDTO.priority())
                .map(Priority::valueOf)
                .ifPresent(task::setPriority);
        Optional.ofNullable(taskDTO.assigneeEmail())
                .flatMap(userRepository::findByEmail)
                .ifPresent(task::setAssignee);

        taskRepository.save(task);

    }

    public void updateTaskStatus(Long id, StatusTaskDTO statusTaskDTO) {
        Task task = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);

        task.setStatus(Status.valueOf(statusTaskDTO.status()));
        task.setUpdatedAt(statusTaskDTO.updatedAt());

        taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);

        taskRepository.delete(task);
    }

    private TaskDTO taskToDTO(Task task) {
        return new TaskDTO(
                task.getTitle(),
                task.getDescription(),
                task.getStatus().name(),
                task.getPriority().name(),
                task.getAuthor().getEmail(),
                task.getAssignee().getEmail() != null ? task.getAssignee().getEmail() : null,
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }

    private Task toEntity(TaskDTO taskDTO) {
        Task task = new Task();
        User author = userRepository.findByEmail(taskDTO.authorEmail()).orElseThrow(UserNotFoundException::new),
                assignee = userRepository.findByEmail(taskDTO.assigneeEmail()).orElseThrow(UserNotFoundException::new);

        task.setTitle(taskDTO.title());
        task.setDescription(taskDTO.description());
        task.setStatus(Status.valueOf(taskDTO.status()));
        task.setPriority(Priority.valueOf(taskDTO.priority()));
        task.setAuthor(author);
        task.setAssignee(assignee);

        return task;
    }
}
