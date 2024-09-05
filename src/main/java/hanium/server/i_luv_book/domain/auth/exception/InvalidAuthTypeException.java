package hanium.server.i_luv_book.domain.auth.exception;

public class InvalidAuthTypeException extends RuntimeException {
    public InvalidAuthTypeException(String message) {
        super(message);
    }
}
