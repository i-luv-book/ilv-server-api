package hanium.server.i_luv_book.security.authentication.filter;

import hanium.server.i_luv_book.config.SecurityConfig;
import hanium.server.i_luv_book.jwt.utils.JwtUtil;
import hanium.server.i_luv_book.security.authentication.provider.JwtAuthenticationProvider;
import hanium.server.i_luv_book.security.authentication.token.JwtAuthenticationToken;
import hanium.server.i_luv_book.user.Role;
import io.jsonwebtoken.JwtException;
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

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath();

        if (List.of(SecurityConfig.WHITE_LIST).contains(path)) {
            filterChain.doFilter(request,response);
            return;
        }

        String token = getTokenAndValidateJwtRequest(request);
        log.info("입력된 token {}",token);

        Role role = jwtUtil.getRoleFromToken(token);
        Long userId= jwtUtil.getUserIdFromToken(token);

        log.info("userid = {} , role = {}",userId,role.name());

        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) jwtAuthenticationProvider.authenticate(userId,role);
//        log.info("JwtAuthToken {}",authenticationToken.toString());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //SecurityContextHolder.getContextHolderStrategy().getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request,response);
    }

    private String getTokenAndValidateJwtRequest(HttpServletRequest request) {

        String jwtHeader = request.getHeader("Authorization");

        if (!StringUtils.hasText(jwtHeader)) {
            throw new JwtException("token is not provided");
        }

        if (!jwtHeader.startsWith("Bearer ")) {
            throw new JwtException("Bearer is not provided");
        }

        return jwtHeader.replace("Bearer ","");
    }


}
