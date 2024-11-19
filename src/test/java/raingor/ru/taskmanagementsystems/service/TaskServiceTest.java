package raingor.ru.taskmanagementsystems.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import raingor.ru.taskmanagementsystems.domain.*;
import raingor.ru.taskmanagementsystems.dto.TaskDTO;
import raingor.ru.taskmanagementsystems.repository.TaskRepository;
import raingor.ru.taskmanagementsystems.repository.UserRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void testGetAllTasks() {
        List<Comment> comments = List.of(new Comment(), new Comment());
        Task task = new Task();

        task.setId(1L);
        task.setTitle("Task Title");
        task.setDescription("Description of the task");
        task.setStatus(Status.PENDING);
        task.setPriority(Priority.HIGH);
        task.setAuthor(new User());
        task.setAssignee(new User());
        task.setComments(comments);
        task.setCreatedAt(OffsetDateTime.now());
        task.setUpdatedAt(OffsetDateTime.now());

        when(taskRepository.findAll()).thenReturn(List.of(task));

        List<TaskDTO> tasks = taskService.getAllTasks(null);

        assertNotNull(tasks);
        assertEquals(1, tasks.size());
    }

    @Test
    void testAddTask() {
        TaskDTO taskDTO = new TaskDTO("New Task", "Description", "PENDING", "HIGH", "author@example.com", "assignee@example.com", null, null);
        User author = User.builder().email("author@example.com").build();
        User assignee = User.builder().email("assignee@example.com").build();
        when(userRepository.findByEmail("author@example.com")).thenReturn(Optional.of(author));
        when(userRepository.findByEmail("assignee@example.com")).thenReturn(Optional.of(assignee));

        assertDoesNotThrow(() -> taskService.addTask(taskDTO));
    }

    @Test
    void testUpdateTask() {
        Task task = Task.builder()
                .id(1L)
                .title("Old Task")
                .description("Old Description")
                .status(Status.PENDING)
                .priority(Priority.LOW)
                .build();
        TaskDTO taskDTO = new TaskDTO("Updated Task", "Updated Description", "IN_PROGRESS", "HIGH", null, null, null, null);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(task);

        assertDoesNotThrow(() -> taskService.updateTask(1L, taskDTO));
        verify(taskRepository, times(1)).save(task);

        assertEquals("Updated Task", task.getTitle());
        assertEquals("Updated Description", task.getDescription());
        assertEquals(Status.IN_PROGRESS, task.getStatus());
        assertEquals(Priority.HIGH, task.getPriority());
    }
}

