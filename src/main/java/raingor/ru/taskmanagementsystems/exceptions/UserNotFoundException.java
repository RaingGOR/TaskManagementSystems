package raingor.ru.taskmanagementsystems.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ApiException {
    public UserNotFoundException() {
        super("User not found!", HttpStatus.NOT_FOUND);
    }
}
