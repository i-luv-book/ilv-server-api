package hanium.server.i_luv_book.global.security.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import hanium.server.i_luv_book.global.security.exception.code.SecurityErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

import static hanium.server.i_luv_book.global.security.exception.code.SecurityExceptionCode.*;
/**
 * @author Young9
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    ObjectMapper mapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        SecurityErrorResponse errorResponse = new SecurityErrorResponse(
                ACCESS_DENIED.getStatus().value(),ACCESS_DENIED.getStatus().name(), ACCESS_DENIED.getCode(), ACCESS_DENIED.getMessage());

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(mapper.writeValueAsString(errorResponse));
    }
}
