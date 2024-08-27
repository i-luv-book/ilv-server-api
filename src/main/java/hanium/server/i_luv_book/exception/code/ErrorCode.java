package hanium.server.i_luv_book.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * @author ijin
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Common
    INVALID_REQUEST_PARAMETER(CommonCode.INVALID_REQUEST_PARAMETER.getCode(), HttpStatus.BAD_REQUEST, "Invalid request parameter."),
    METHOD_NOT_ALLOWED(CommonCode.METHOD_NOT_ALLOWED.getCode(), HttpStatus.METHOD_NOT_ALLOWED, "Method not allowed."),
    INVALID_REQUEST_URI(CommonCode.INVALID_REQUEST_URI.getCode(), HttpStatus.NOT_FOUND, "Invalid request URI."),
    VIOLATED_DATA_INTEGRITY(CommonCode.VIOLATED_DATA_INTEGRITY.getCode(), HttpStatus.CONFLICT, "Data integrity violation."),
    FAILED_DATA_IO(CommonCode.FAILED_DATA_IO.getCode(), HttpStatus.INTERNAL_SERVER_ERROR, "Data I/O error."),
    SERVICE_UNAVAILABLE(CommonCode.SERVICE_UNAVAILABLE.getCode(), HttpStatus.SERVICE_UNAVAILABLE, "Service is currently unavailable."),

    // User
    USER_NOT_FOUND(UserCode.NOT_FOUND.getCode(), HttpStatus.NOT_FOUND, "User not found."),
    USER_ALREADY_EXISTED(UserCode.ALREADY_EXISTED.getCode(), HttpStatus.CONFLICT, "User already exists."),
    LIMITED_ACCESS(UserCode.LIMITED_ACCESS.getCode(), HttpStatus.FORBIDDEN, "Access is restricted because of membership."),

    // File
    EMPTY_FILE(FileCode.EMPTY.getCode(), HttpStatus.BAD_REQUEST, "The file is empty."),
    NON_EXISTENT_EXTENSION(FileCode.NON_EXISTENT_EXTENSION.getCode(), HttpStatus.BAD_REQUEST, "The file has no extension."),
    INVALID_EXTENSION(FileCode.INVALID_EXTENSION.getCode(), HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Invalid file extension."),
    LIMITED_SIZE(FileCode.LIMITED_SIZE.getCode(), HttpStatus.PAYLOAD_TOO_LARGE, "The file size exceeds the limit."),
    FAILED_IO(FileCode.FAILED_IO.getCode(), HttpStatus.INTERNAL_SERVER_ERROR, "Failed to perform I/O operation."),
    FAILED_PUT_OBJECT(FileCode.FAILED_PUT_OBJECT.getCode(), HttpStatus.INTERNAL_SERVER_ERROR, "Failed to upload file to the storage."),
    FAILED_DELETE_OBJECT(FileCode.FAILED_DELETE_OBJECT.getCode(), HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete file from the storage.");

    private final String code;
    private final HttpStatus status;
    private final String message;
}
