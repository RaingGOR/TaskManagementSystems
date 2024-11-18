package raingor.ru.taskmanagementsystems.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import raingor.ru.taskmanagementsystems.dto.CommentDTO;
import raingor.ru.taskmanagementsystems.service.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks/{id}/comments")
public class CommentController {
    private final CommentService commentService;

    @GetMapping
    public List<CommentDTO> getComments(@PathVariable Long id) {
        return commentService.getComments(id);
    }

    @PostMapping
    public HttpStatus addComment(@PathVariable Long id, @RequestBody CommentDTO commentDTO) {
        commentService.addComment(id, commentDTO);

        return HttpStatus.CREATED;
    }
}
