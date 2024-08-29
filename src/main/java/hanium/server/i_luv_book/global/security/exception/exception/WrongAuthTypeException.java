package hanium.server.i_luv_book.global.security.exception.exception;

import io.jsonwebtoken.JwtException;
/**
 * @author Young9
 */
public class WrongAuthTypeException extends JwtException {
    public WrongAuthTypeException(String message) {
        super(message);
    }
}
