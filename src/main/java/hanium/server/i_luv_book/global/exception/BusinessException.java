package hanium.server.i_luv_book.global.exception;

import hanium.server.i_luv_book.global.exception.code.ErrorCode;
import lombok.Getter;

/**
 * Custom Exception's Class
 *
 * @author ijin
 */
@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
