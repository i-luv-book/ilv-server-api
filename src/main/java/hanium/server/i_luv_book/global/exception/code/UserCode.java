package hanium.server.i_luv_book.global.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author ijin
 */
@Getter
@RequiredArgsConstructor
public enum UserCode {
    NOT_FOUND("U-01"),
    ALREADY_EXISTED("U-02"),
    LIMITED_ACCESS("U-03"),
    BADGE_NOT_FOUND("U-04"),
    FCM_TOKEN_NOT_FOUND("U-05")
    ;

    private final String code;
}
