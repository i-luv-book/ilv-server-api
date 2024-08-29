package hanium.server.i_luv_book.global.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import hanium.server.i_luv_book.global.exception.code.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

/**
 * @author ijin
 */
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    /**
     * status, error, code, message - ErrorCode's Infos
     * detail - ErrorCode's Custom Messages
     */
    private final int status;
    private final String error;
    private final String code;
    private final String message;
    private final String detail;

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ErrorResponse.builder()
                        .status(errorCode.getStatus().value())
                        .error(errorCode.getStatus().name())
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode, String message) {
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ErrorResponse.builder()
                        .status(errorCode.getStatus().value())
                        .error(errorCode.getStatus().name())
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .detail(message)
                        .build());
    }

    @Override
    public String toString() {
        return "{\n" +
                "    \"status\":" + status + ",\n" +
                "    \"error\":\"" + error + "\",\n" +
                "    \"code\":\"" + code + "\",\n" +
                "    \"message\":\"" + message + "\"\n" +
                "}";
    }
}
