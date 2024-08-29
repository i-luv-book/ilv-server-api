package hanium.server.i_luv_book.global.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author ijin
 */
@Getter
@RequiredArgsConstructor
public enum CommonCode {
    INVALID_REQUEST_PARAMETER("C-01"),
    METHOD_NOT_ALLOWED("C-02"),
    INVALID_REQUEST_URI("C-03"),
    VIOLATED_DATA_INTEGRITY("C-04"),
    FAILED_DATA_IO("C-05"),
    SERVICE_UNAVAILABLE("C-06"),
    ;

    private final String code;
}
