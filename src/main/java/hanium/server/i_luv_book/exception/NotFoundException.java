package hanium.server.i_luv_book.exception;

import hanium.server.i_luv_book.exception.code.ErrorCode;

/**
 * @author ijin
 */
public class NotFoundException extends BusinessException {

    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NotFoundException(ErrorCode errorCode, long id) {
        super(errorCode, "id " + id + " is not found");
    }
}
