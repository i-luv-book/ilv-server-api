package hanium.server.i_luv_book.security.exception.exception;

import io.jsonwebtoken.JwtException;
/**
 * @author Young9
 */
public class InvalidJwtException extends JwtException {
    public InvalidJwtException(String message) {
        super(message);
    }
}
