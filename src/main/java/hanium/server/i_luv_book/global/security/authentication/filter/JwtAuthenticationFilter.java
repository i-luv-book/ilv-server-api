package hanium.server.i_luv_book.global.security.authentication.filter;


import hanium.server.i_luv_book.global.config.security.SecurityConfig;
import hanium.server.i_luv_book.global.jwt.util.JwtUtil;
import hanium.server.i_luv_book.global.security.authentication.provider.JwtAuthenticationProvider;
import hanium.server.i_luv_book.global.security.authentication.token.JwtAuthenticationToken;
import hanium.server.i_luv_book.global.security.exception.code.SecurityExceptionCode;
import hanium.server.i_luv_book.domain.user.domain.Role;
import hanium.server.i_luv_book.global.security.exception.jwt.exception.CustomExpiredJwtException;
import hanium.server.i_luv_book.global.security.exception.jwt.exception.EmptyJwtException;
import hanium.server.i_luv_book.global.security.exception.jwt.exception.InvalidJwtException;
import hanium.server.i_luv_book.global.security.exception.jwt.exception.InvalidAuthTypeException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Young9
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String path = request.getServletPath();
            if (List.of(SecurityConfig.WHITE_LIST).contains(path)) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = getTokenAndValidateJwtRequest(request);
            Role role = jwtUtil.getRoleFromToken(token)
                    .orElse(Role.ROLE_FREE);
            Long userId = jwtUtil.getUserIdFromToken(token);
            String uuid = jwtUtil.getUuidFromToken(token);

            log.info("userId = {}, role = {} ",userId.toString(),role.name());
            JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) jwtAuthenticationProvider.authenticate(userId,role,uuid);
            SecurityContextHolder.getContextHolderStrategy().getContext().setAuthentication(authenticationToken);

        } catch (CustomExpiredJwtException ex) {
            request.setAttribute("exceptionCode", SecurityExceptionCode.EXPIRED_TOKEN);
        } catch (EmptyJwtException ex) {
            request.setAttribute("exceptionCode", SecurityExceptionCode.TOKEN_CAN_NOT_BE_NULL);
        } catch (InvalidJwtException ex) {
            request.setAttribute("exceptionCode", SecurityExceptionCode.TOKEN_NOT_VALID);
        } catch (InvalidAuthTypeException ex) {
            request.setAttribute("exceptionCode", SecurityExceptionCode.AUTHTYPE_NOT_VALID);
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenAndValidateJwtRequest(HttpServletRequest request) {

        String jwtHeader = request.getHeader("Authorization");
        if (!StringUtils.hasText(jwtHeader)) {
            throw new EmptyJwtException("token is not provided");
        }

        if (!jwtHeader.startsWith("Bearer ")) {
            throw new InvalidAuthTypeException("Bearer is not provided");
        }

        return jwtHeader.replace("Bearer ","");
    }


}
