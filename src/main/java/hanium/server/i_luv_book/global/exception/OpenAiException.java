package hanium.server.i_luv_book.global.exception;

import hanium.server.i_luv_book.global.exception.code.ErrorCode;

/**
 * @author ijin
 */
public class OpenAiException extends BusinessException {
    public OpenAiException(ErrorCode errorCode) {
        super(errorCode);
    }

    public OpenAiException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
