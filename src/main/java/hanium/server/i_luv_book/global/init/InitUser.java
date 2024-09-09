package hanium.server.i_luv_book.global.init;

import hanium.server.i_luv_book.domain.auth.application.LoginService;
import hanium.server.i_luv_book.domain.auth.domain.LoginType;
import hanium.server.i_luv_book.domain.auth.dto.response.JwtTokenResponse;
import hanium.server.i_luv_book.domain.user.domain.Parent;
import hanium.server.i_luv_book.domain.user.domain.Role;
import hanium.server.i_luv_book.domain.user.domain.UserRepository;
import hanium.server.i_luv_book.global.jwt.dao.RefreshTokenRepository;
import hanium.server.i_luv_book.global.jwt.domain.RefreshToken;
import hanium.server.i_luv_book.global.jwt.util.JwtUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Component
@Slf4j
public class InitUser {

    private final InitUserService initUserService;

    @PostConstruct
    public void initUser() {
        initUserService.initUser();
    }
}