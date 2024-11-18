package raingor.ru.taskmanagementsystems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raingor.ru.taskmanagementsystems.domain.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
