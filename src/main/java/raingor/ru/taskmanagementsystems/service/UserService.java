package raingor.ru.taskmanagementsystems.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import raingor.ru.taskmanagementsystems.domain.Role;
import raingor.ru.taskmanagementsystems.domain.User;
import raingor.ru.taskmanagementsystems.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User getUserByUsername(String username) {
        return userRepository.findByName(username).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void createUser(User user) {
        if (userRepository.existsByName(user.getUsername())) {
            // Заменить на свои исключения
            throw new RuntimeException("Пользователь с таким именем уже существует");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Пользователь с таким email уже существует");
        }

        userRepository.save(user);
    }

    public User getCurrentUser() {
        // Получение имени пользователя из контекста Spring Security
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return this.getUserByUsername(username);
    }

    public UserDetailsService userDetailsService() {
        return this::getUserByUsername;
    }

    @Deprecated
    public void getAdmin() {
        var user = getCurrentUser();
        user.setRole(Role.ROLE_ADMIN);
        userRepository.save(user);
    }
}
