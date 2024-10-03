package hanium.server.i_luv_book.global.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FairytaleCode {
    NOT_FOUND("F-01"),
    ;

    private final String code;
}
