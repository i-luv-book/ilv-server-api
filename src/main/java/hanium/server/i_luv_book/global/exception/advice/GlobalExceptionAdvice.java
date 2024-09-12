package hanium.server.i_luv_book.global.exception.advice;

import hanium.server.i_luv_book.domain.auth.exception.RefreshTokenNotFoundException;
import hanium.server.i_luv_book.global.exception.BusinessException;
import hanium.server.i_luv_book.global.exception.ErrorResponse;
import hanium.server.i_luv_book.global.exception.code.ErrorCode;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import javax.naming.SizeLimitExceededException;
import java.time.format.DateTimeParseException;
import java.util.stream.Collectors;

/**
 * @author ijin
 */
@RestControllerAdvice
public class GlobalExceptionAdvice {

    // 비즈니스 예외 처리시 발생
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        return createErrorResponse(e, e.getErrorCode());
    }

    // 1.DateTime 포맷 형식 에러
    // 2.@Valid or @Validated 으로 binding error 발생시 발생
    @ExceptionHandler({MethodArgumentNotValidException.class, DateTimeParseException.class})
    protected ResponseEntity<ErrorResponse> methodArgumentValidation(Exception e) {
        return createErrorResponse(e, ErrorCode.INVALID_REQUEST_PARAMETER);
    }

    // @ModelAttribute 으로 바인딩 에러시 발생
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponse> bindException(BindException e) {
        return createErrorResponse(e, ErrorCode.INVALID_REQUEST_PARAMETER);
    }

    // 지원하지 않은 HTTP Method 호출 할 경우 발생
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> requestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return createErrorResponse(e, ErrorCode.METHOD_NOT_ALLOWED);
    }

    // 데이터 잘못 넘어갔을 경우 발생
    @ExceptionHandler(IllegalStateException.class)
    protected ResponseEntity<ErrorResponse> illegalArgumentException(IllegalArgumentException e) {
        return createErrorResponse(e, ErrorCode.INVALID_REQUEST_PARAMETER);
    }

    // 데이터 무결성 위반한 경우 발생
    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<ErrorResponse> dataIntegrityViolationException(DataIntegrityViolationException e) {
        return createErrorResponse(e, ErrorCode.VIOLATED_DATA_INTEGRITY);
    }

    // 이미지 크기 초과시 발생
    @ExceptionHandler({MaxUploadSizeExceededException.class, SizeLimitExceededException.class, MultipartException.class})
    protected ResponseEntity<ErrorResponse> imageFileSizeExceedException(Exception e) {
        return createErrorResponse(e, ErrorCode.LIMITED_SIZE);
    }

    // RequestPart&Param 요청에서 빠진 파라미터가 있을 때
    @ExceptionHandler({MissingServletRequestPartException.class, MissingServletRequestParameterException.class})
    protected ResponseEntity<ErrorResponse> missingServletRequestPartException(Exception e) {
        return createErrorResponse(e, ErrorCode.INVALID_REQUEST_PARAMETER);
    }

    // 설계되지 않은 URI 로 요청한 경우
    @ExceptionHandler(NoResourceFoundException.class)
    protected ResponseEntity<ErrorResponse> noResourceFoundException(NoResourceFoundException e) {
        return createErrorResponse(e, ErrorCode.INVALID_REQUEST_URI);
    }

    // 리프레시 토큰이 DB에 없는 경우
    @ExceptionHandler(RefreshTokenNotFoundException.class)
    protected ResponseEntity<ErrorResponse> refreshTokenNotFoundException(RefreshTokenNotFoundException e) {
        return createErrorResponse(e,ErrorCode.REFRESH_TOKEN_NOT_FOUND);
    }

    // 나머지 에러 여기서 핸들링
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        System.out.println(e.getMessage()+ "  유형은 ? "+ e.getClass());
        return createErrorResponse(e, ErrorCode.SERVICE_UNAVAILABLE);
    }

    // Create ExceptionResponse
    private ResponseEntity<ErrorResponse> createErrorResponse(Exception e, ErrorCode errorCode) {
        ResponseEntity<ErrorResponse> response;
        if (e.getClass().equals(MethodArgumentNotValidException.class)) {
            // MethodArgumentNotValidException 인 경우, 어떤 파라미터가 유효하지 못한지 ErrorResponse 에 정보 추가
            response = ErrorResponse.toResponseEntity(ErrorCode.INVALID_REQUEST_PARAMETER,
                    ((MethodArgumentNotValidException) e).getBindingResult().getFieldErrors().stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(" and ")));
        } else {
            response = ErrorResponse.toResponseEntity(errorCode);
        }

        return response;
    }
}
