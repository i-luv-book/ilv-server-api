package hanium.server.i_luv_book.domain.auth.exception;
/**
 * @author Young9
 */
public class RefreshTokenNotFoundException extends RuntimeException{
    public RefreshTokenNotFoundException(String message) {
        super(message);
    }
}
