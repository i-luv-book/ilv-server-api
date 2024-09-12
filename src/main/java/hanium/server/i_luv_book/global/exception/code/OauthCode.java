package hanium.server.i_luv_book.global.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OauthCode {

    KAKAO_CANNOT_CONNECTION("AUTH-007"),
    GOOGLE_CANNOT_CONNECTION("AUTH-008");

    private final String code;
}
