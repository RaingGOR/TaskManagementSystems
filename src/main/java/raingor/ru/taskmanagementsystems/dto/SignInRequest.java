package raingor.ru.taskmanagementsystems.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на аутентификацию")
public class SignInRequest {

    @Schema(description = "Имя пользователя", example = "Jon")
    @Size(min = 5, max = 32, message = "Имя пользователя должно содержать от 5 до 32 символов")
    @NotBlank(message = "Имя пользователя не может быть пустыми")
    private String username;

    @Schema(description = "Пароль", example = "my_1secret1_password")
    @Size(min = 8, max = 100, message = "Длина пароля должна быть от 8 до 100 символов")
    @NotBlank(message = "Пароль не может быть пустыми")
    private String password;
}