package raingor.ru.taskmanagementsystems.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import raingor.ru.taskmanagementsystems.domain.Comment;
import raingor.ru.taskmanagementsystems.domain.Task;
import raingor.ru.taskmanagementsystems.domain.User;
import raingor.ru.taskmanagementsystems.dto.CommentDTO;
import raingor.ru.taskmanagementsystems.exceptions.TaskNotFoundException;
import raingor.ru.taskmanagementsystems.repository.CommentRepository;
import raingor.ru.taskmanagementsystems.repository.TaskRepository;
import raingor.ru.taskmanagementsystems.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public List<CommentDTO> getComments(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);

        return task.getComments().stream().map(this::toDTO).toList();
    }

    public void addComment(Long taskId, CommentDTO commentDTO) {
        User author = userRepository.findByEmail(commentDTO.authorEmail()).orElseThrow(TaskNotFoundException::new);
        Task task = taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
        Comment createdComment = toEntity(commentDTO, author, task);

        commentRepository.save(createdComment);
    }


    private CommentDTO toDTO(Comment comment) {
        return new CommentDTO(
                comment.getAuthor() != null ? comment.getAuthor().getEmail() : null,
                comment.getTask() != null ? comment.getTask().getTitle() : null,
                comment.getText(),
                comment.getCreatedAt()
        );
    }

    private Comment toEntity(CommentDTO commentDTO, User author, Task task) {
        Comment comment = new Comment();

        comment.setAuthor(author);
        comment.setTask(task);
        comment.setText(commentDTO.text());
        comment.setCreatedAt(commentDTO.createdAt());

        return comment;
    }
}
