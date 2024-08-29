package hanium.server.i_luv_book.config;

import hanium.server.i_luv_book.security.authentication.filter.JwtAuthenticationFilter;
import hanium.server.i_luv_book.security.exception.entrypoint.CustomAuthenticationEntryPoint;
import hanium.server.i_luv_book.security.exception.handler.CustomAccessDeniedHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * @author Young9
 */

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthorizationManager<RequestAuthorizationContext> authorizationManager;
    public static final String[] WHITE_LIST = {"/api/login/oauth", "/api/signup/oauth/kakao", "/","/auth","/refreshToken"};
    public static final String[] PAID_LIST = {"/api/badge"};
    public static final String[] FREE_LIST = {"/api/fairytale/**"};
    public static final String[] ADMIN_LIST = {"/api/admin/**"};

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
        http
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //모든 요청은 커스텀 인가 매니저를 통해 인가처리.
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().access(authorizationManager))
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception ->exception
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                        .accessDeniedHandler(new CustomAccessDeniedHandler()))

        ;
        return http.build();
    }

    //    인가를 위한 계층적 권한 생성

}
