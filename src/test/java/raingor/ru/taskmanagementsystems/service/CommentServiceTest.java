package raingor.ru.taskmanagementsystems.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import raingor.ru.taskmanagementsystems.domain.*;
import raingor.ru.taskmanagementsystems.dto.CommentDTO;
import raingor.ru.taskmanagementsystems.repository.CommentRepository;
import raingor.ru.taskmanagementsystems.repository.TaskRepository;
import raingor.ru.taskmanagementsystems.repository.UserRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CommentService commentService;

    @Test
    void testGetComments() {
        Task task = Task.builder()
                .id(1L)
                .title("Task 1")
                .build();
        Comment comment = Comment.builder()
                .id(1L)
                .text("Test comment")
                .author(User.builder().email("author@example.com").build())
                .task(task)
                .createdAt(OffsetDateTime.now())
                .build();
        task.setComments(List.of(comment));
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        List<CommentDTO> comments = commentService.getComments(1L);

        assertNotNull(comments);
        assertEquals(1, comments.size());
    }

    @Test
    void testAddComment() {
        CommentDTO commentDTO = new CommentDTO("author@example.com", "Task 1", "This is a comment", OffsetDateTime.now());
        User author = User.builder().email("author@example.com").build();
        Task task = Task.builder().id(1L).title("Task 1").build();

        when(userRepository.findByEmail("author@example.com")).thenReturn(Optional.of(author));
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(commentRepository.save(any(Comment.class))).thenReturn(new Comment());

        assertDoesNotThrow(() -> commentService.addComment(1L, commentDTO));
        verify(commentRepository, times(1)).save(any(Comment.class));
    }
}
