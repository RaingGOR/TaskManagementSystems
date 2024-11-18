package raingor.ru.taskmanagementsystems.dto;

import java.time.OffsetDateTime;

public record TaskDTO(
        String title,
        String description,
        String status,
        String priority,
        String authorEmail,
        String assigneeEmail,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
