package hanium.server.i_luv_book.global.security.exception.jwt.exception;

import io.jsonwebtoken.JwtException;
/**
 * @author Young9
 */
public class InvalidAuthTypeException extends JwtException {
    public InvalidAuthTypeException(String message) {
        super(message);
    }
}
