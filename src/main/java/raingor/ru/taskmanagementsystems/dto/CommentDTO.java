package raingor.ru.taskmanagementsystems.dto;

import java.time.OffsetDateTime;

public record CommentDTO(
        String authorEmail,
        String task,
        String text,
        OffsetDateTime createdAt
) {
}
