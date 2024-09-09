package hanium.server.i_luv_book.global.jwt.application;


import hanium.server.i_luv_book.domain.auth.dto.response.JwtTokenResponse;
import hanium.server.i_luv_book.domain.auth.exception.RefreshTokenNotFoundException;
import hanium.server.i_luv_book.domain.user.domain.Parent;
import hanium.server.i_luv_book.global.jwt.dao.RefreshTokenRepository;
import hanium.server.i_luv_book.global.jwt.domain.RefreshToken;
import hanium.server.i_luv_book.global.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;
    //삭제, 저장

    @Transactional
    public JwtTokenResponse generateJwtToken(Parent parent) {
        UUID uuid = UUID.randomUUID();
        String accessToken = jwtUtil.generateAccessToken(parent.getId(), parent.getRole(),uuid);
        String refreshToken = jwtUtil.generateRefreshToken(parent.getId(),uuid);
        refreshTokenRepository.save(new RefreshToken(String.valueOf(parent.getId()),refreshToken,uuid.toString()));
        return new JwtTokenResponse(accessToken,refreshToken);
    }

    @Transactional
    public void destroyRefreshToken(String uuid) {
        refreshTokenRepository.deleteByUuid(uuid);
    }

}
