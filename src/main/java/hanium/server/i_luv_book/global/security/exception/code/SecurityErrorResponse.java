package hanium.server.i_luv_book.global.security.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
/**
 * @author Young9
 */
@Getter
public class SecurityErrorResponse {
    private int status;
    private String error;
    private String code;
    private String message;

    public SecurityErrorResponse(int status, String error, String code, String message) {
        this.status = status;
        this.error = error;
        this.code = code;
        this.message = message;
    }
}
