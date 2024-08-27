package hanium.server.i_luv_book.security.exception.jwt.exception;

import io.jsonwebtoken.JwtException;
/**
 * @author Young9
 */
public class CustomExpiredJwtException extends JwtException {
    public CustomExpiredJwtException(String message) {
        super(message);
    }
}
