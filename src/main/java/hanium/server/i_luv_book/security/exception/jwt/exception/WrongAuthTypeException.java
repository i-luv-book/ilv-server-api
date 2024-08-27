package hanium.server.i_luv_book.security.exception.jwt.exception;

import io.jsonwebtoken.JwtException;
/**
 * @author Young9
 */
public class WrongAuthTypeException extends JwtException {
    public WrongAuthTypeException(String message) {
        super(message);
    }
}
