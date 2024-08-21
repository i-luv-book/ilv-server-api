package hanium.server.i_luv_book.exception;

import hanium.server.i_luv_book.exception.code.ErrorCode;

/**
 * @author ijin
 */
public class FileIoException extends BusinessException {
    public FileIoException(ErrorCode errorCode) {
        super(errorCode);
    }
}
