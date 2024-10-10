package hanium.server.i_luv_book.global.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EducationCode {
    QUIZ_NOT_FOUND("E-01"),
    QUIZ_ALREADY_EXIST("E-02"),
    WORDS_NOT_FOUND("E-03"),
    WORDS_ALREADY_EXIST("E-04"),
    ;

    private final String code;
}
