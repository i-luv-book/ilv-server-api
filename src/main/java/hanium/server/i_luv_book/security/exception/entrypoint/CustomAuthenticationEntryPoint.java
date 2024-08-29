package hanium.server.i_luv_book.security.exception.entrypoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import hanium.server.i_luv_book.security.exception.code.SecurityErrorResponse;
import hanium.server.i_luv_book.security.exception.code.SecurityExceptionCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
/**
 * @author Young9
 */
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper mapper = new ObjectMapper();
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        SecurityExceptionCode exceptionCode = (SecurityExceptionCode) request.getAttribute("exceptionCode");

        if (exceptionCode != null){
            SecurityErrorResponse errorResponse = new SecurityErrorResponse(exceptionCode.getStatus().value(),
                    exceptionCode.getStatus().name(), exceptionCode.getCode(), exceptionCode.getMessage());

            response.setStatus(exceptionCode.getStatus().value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(mapper.writeValueAsString(errorResponse));
        }
    }
}
