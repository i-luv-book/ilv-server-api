package hanium.server.i_luv_book.global.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author ijin
 */
@Getter
@RequiredArgsConstructor
public enum OpenAiCode {
    FAILED_OPENAI_REQUEST("O-01"),
    UNSUITABLE_OPENAI_RESPONSE("O-02"),
    ;

    private final String code;
}
