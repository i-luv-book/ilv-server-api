package hanium.server.i_luv_book.global.exception;

import hanium.server.i_luv_book.global.exception.code.ErrorCode;

/**
 * @author ijin
 */
public class FileIoException extends BusinessException {
    public FileIoException(ErrorCode errorCode) {
        super(errorCode);
    }
}
