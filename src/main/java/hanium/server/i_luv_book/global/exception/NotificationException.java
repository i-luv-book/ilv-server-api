package hanium.server.i_luv_book.global.exception;

import hanium.server.i_luv_book.global.exception.code.ErrorCode;

public class NotificationException extends BusinessException {
    public NotificationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
