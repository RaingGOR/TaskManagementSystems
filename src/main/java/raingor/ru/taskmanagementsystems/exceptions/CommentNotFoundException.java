package raingor.ru.taskmanagementsystems.exceptions;

import org.springframework.http.HttpStatus;

public class CommentNotFoundException extends ApiException{
    public CommentNotFoundException() {
        super("Comment not found!", HttpStatus.NOT_FOUND);
    }
}
