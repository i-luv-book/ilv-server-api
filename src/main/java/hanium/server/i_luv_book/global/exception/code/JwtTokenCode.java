package hanium.server.i_luv_book.global.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JwtTokenCode {

    REFRESH_TOKEN_NOT_FOUND("AUTH-006");

    private final String code;
}
