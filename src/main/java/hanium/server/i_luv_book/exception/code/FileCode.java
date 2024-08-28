package hanium.server.i_luv_book.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author ijin
 */
@Getter
@RequiredArgsConstructor
public enum FileCode {
    EMPTY_FILE("F-01"),
    EMPTY_EXTENSION("F-02"),
    INVALID_EXTENSION("F-03"),
    LIMITED_SIZE("F-04"),
    FAILED_IO("F-05"),
    FAILED_PUT_OBJECT("F-06"),
    FAILED_DELETE_OBJECT("F-07"),
    ;

    private final String code;
}

