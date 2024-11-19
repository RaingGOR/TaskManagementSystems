package raingor.ru.taskmanagementsystems.dto;

import java.time.OffsetDateTime;

public record StatusTaskDTO(
        Long id,
        String status,
        OffsetDateTime updatedAt
) {
}
