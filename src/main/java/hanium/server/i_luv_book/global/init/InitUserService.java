package hanium.server.i_luv_book.global.init;

import hanium.server.i_luv_book.domain.auth.domain.LoginType;
import hanium.server.i_luv_book.domain.auth.dto.response.JwtTokenResponse;
import hanium.server.i_luv_book.domain.user.domain.Parent;
import hanium.server.i_luv_book.domain.user.domain.Role;
import hanium.server.i_luv_book.domain.user.domain.UserRepository;
import hanium.server.i_luv_book.global.jwt.dao.RefreshTokenRepository;
import hanium.server.i_luv_book.global.jwt.domain.RefreshToken;
import hanium.server.i_luv_book.global.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class InitUserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional  // 트랜잭션 관리
    public void initUser() {
        Parent user1 = new Parent("User1@gmail.com", Parent.MembershipType.FREE, Role.ROLE_FREE, LoginType.KAKAO, "1");
        userRepository.save(user1);
        JwtTokenResponse jwtTokenResponse = generateJwtToken(user1);
        log.info("Free User Access Token : {}", jwtTokenResponse.getAccessToken());

        Parent user2 = new Parent("User2@gmail.com", Parent.MembershipType.PAID_PREMIUM, Role.ROLE_PAID, LoginType.KAKAO, "2");
        userRepository.save(user2);
        JwtTokenResponse jwtTokenResponse2 = generateJwtToken(user2);
        log.info("PAID User Access Token : {}", jwtTokenResponse2.getAccessToken());
    }

    private JwtTokenResponse generateJwtToken(Parent parent) {
        UUID uuid = UUID.randomUUID();
        String accessToken = jwtUtil.generateAccessToken(parent.getId(), parent.getRole(),uuid);
        String refreshToken = jwtUtil.generateRefreshToken(parent.getId(),uuid);
        refreshTokenRepository.save(new RefreshToken(String.valueOf(parent.getId()),refreshToken,uuid.toString()));
        return new JwtTokenResponse(accessToken,refreshToken);
    }
}