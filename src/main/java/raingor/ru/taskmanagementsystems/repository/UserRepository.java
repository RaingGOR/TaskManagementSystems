package raingor.ru.taskmanagementsystems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raingor.ru.taskmanagementsystems.domain.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String username);
    boolean existsByName(String username);
    boolean existsByEmail(String email);
}