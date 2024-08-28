package hanium.server.i_luv_book.security.exception.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;
/**
 * @author Young9
 */
@Getter
public enum SecurityExceptionCode {

    // 인증 에러
    TOKEN_CAN_NOT_BE_NULL(UNAUTHORIZED,"AUTH_001","토큰을 넣지 않았습니다."),
    TOKEN_NOT_VALID(UNAUTHORIZED,"AUTH_002","잘못된 형식의 토큰입니다."),
    AUTHTYPE_NOT_VALID(UNAUTHORIZED,"AUTH_003","인증 형식(Bearer)이 잘못되었습니다."),
    EXPIRED_TOKEN(UNAUTHORIZED,"AUTH_004","토큰이 만료되었습니다."),

    // 인가 에러
    ACCESS_DENIED(FORBIDDEN,"AUTH_005","권한이 없습니다."),
    UNKNOWN_ERROR(SERVICE_UNAVAILABLE,"AUTH_006","알 수 없는 에러입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    SecurityExceptionCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
