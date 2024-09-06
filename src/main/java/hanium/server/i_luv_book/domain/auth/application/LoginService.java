package hanium.server.i_luv_book.domain.auth.application;

import hanium.server.i_luv_book.global.jwt.dao.RefreshTokenRepository;
import hanium.server.i_luv_book.global.jwt.domain.RefreshToken;
import hanium.server.i_luv_book.domain.auth.dto.response.*;
import hanium.server.i_luv_book.domain.auth.exception.RefreshTokenNotFoundException;
import hanium.server.i_luv_book.domain.user.domain.Parent;
import hanium.server.i_luv_book.domain.user.domain.Role;
import hanium.server.i_luv_book.domain.user.domain.UserRepository;
import hanium.server.i_luv_book.domain.auth.config.LoginWebConfig;
import hanium.server.i_luv_book.domain.auth.domain.LoginType;
import hanium.server.i_luv_book.domain.auth.util.LoginWebClientUtil;
import hanium.server.i_luv_book.global.exception.NotFoundException;
import hanium.server.i_luv_book.global.exception.code.ErrorCode;
import hanium.server.i_luv_book.global.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final LoginWebClientUtil webClientUtil;
    private final LoginWebConfig loginWebConfig;
    private final RefreshTokenRepository refreshTokenRepository;
    public LoginFormResDTO getLoginFormUrl(LoginType type) {
        return new LoginFormResDTO(type.getLoginFormUrl());
    }
    @Transactional
    public JwtTokenResponse generateJwtTokens(String code, LoginType type) {
        MultiValueMap<String, String> map = getMultiValueMap(code,type);
        UserInfoDTO userInfo = getUserInfo(map,type);
        Optional<Parent> optionalParent = userRepository.findParentBySocialIdAndLoginType(userInfo.getSocialId(), type);

        if(optionalParent.isPresent()) {
            Parent parent = optionalParent.get();
            return generateJwtToken(parent);
        } else {
            //refator 필요
            Parent parent = new Parent(userInfo.getEmail(), Parent.MembershipType.FREE,Role.ROLE_FREE,type, userInfo.getSocialId());
            userRepository.save(parent);
            return generateJwtToken(parent);
        }
    }

    @Transactional
    public JwtTokenResponse reissueJwtTokens(String refreshToken) {
        Optional<RefreshToken> optionalToken = refreshTokenRepository.findByToken(refreshToken);

        if (optionalToken.isEmpty()) {
            throw new RefreshTokenNotFoundException("존재하지않는 리프레쉬 토큰입니다.");
        } else {
            //Refresh가 DB에 존재하고 유효하다면 토큰들을 재발급해준다.
            String token = optionalToken.get().getToken();
            jwtUtil.validateToken(token);
            Long userId= jwtUtil.getUserIdFromToken(token);
            Parent parent = getParentOrThrow(userId);
            refreshTokenRepository.deleteById(optionalToken.get().getId());
            return generateJwtToken(parent);
        }
    }

    private MultiValueMap<String, String> getMultiValueMap(String code,LoginType type) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        //구글과 카카오의 공통 body
        map.add("grant_type", "authorization_code");
        map.add("code", code);

        switch (type) {
            case GOOGLE:
                map.add("client_id", loginWebConfig.getGoogleClientId());
                map.add("redirect_uri", loginWebConfig.getGoogleRedirectUrl());
                map.add("client_secret", loginWebConfig.getGoogleClientSecret());
                break;
            case KAKAO:
                map.add("client_id", loginWebConfig.getKakaoRestKey());
                map.add("redirect_uri", loginWebConfig.getKakaoRedirectUrl());
                map.add("client_secret", loginWebConfig.getKakaoClientSecret());
        }
        return map;
    }


    private JwtTokenResponse generateJwtToken(Parent parent) {
        UUID uuid = UUID.randomUUID();
        String accessToken = jwtUtil.generateAccessToken(parent.getId(), parent.getRole(),uuid);
        String refreshToken = jwtUtil.generateRefreshToken(parent.getId(),uuid);
        refreshTokenRepository.save(new RefreshToken(String.valueOf(parent.getId()),refreshToken,uuid.toString()));
        return new JwtTokenResponse(accessToken,refreshToken);
    }
    private UserInfoDTO getUserInfo(MultiValueMap<String, String> map, LoginType type) {

        switch (type) {
            case GOOGLE:
                GoogleAccessTokenDTO googleAccessToken = webClientUtil.getAceesTokenFromGoogle(map).block();
                GoogleUserInfoDTO googleUserInfo = webClientUtil.getUserInfoFromGoogle(googleAccessToken.getAccess_token()).block();
                return new UserInfoDTO(googleUserInfo.getId(), googleUserInfo.getEmail());

            //KAKAO
            default:
                KakaoAccessTokenDTO kakaoAccessToken = webClientUtil.getAceessTokenFromKakao(map).block();
                KakaoUserInfoDTO kakaoUserInfo = webClientUtil.getUserInfoFromKakao(kakaoAccessToken.getAccess_token()).block();
                return new UserInfoDTO(kakaoUserInfo.getSub(), kakaoUserInfo.getEmail());
        }
    }

    private Parent getParentOrThrow(long parentId) {
        return userRepository.findParentById(parentId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND,parentId));
    }

    public void destroyRefreshToekn(String accessToken) {
        String uuid = jwtUtil.getUuidFromToken(accessToken);
        RefreshToken refreshToken = refreshTokenRepository.findByUuid(uuid)
                .orElseThrow(() -> new RefreshTokenNotFoundException("존재하지않는 리프레쉬 토큰입니다."));
        refreshTokenRepository.deleteById(refreshToken.getId());
    }
}

