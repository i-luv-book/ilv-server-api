package hanium.server.i_luv_book.global.security.exception.jwt.exception;

import io.jsonwebtoken.JwtException;
/**
 * @author Young9
 */
public class EmptyJwtException extends JwtException {
    public EmptyJwtException(String message) {
        super(message);
    }
}
