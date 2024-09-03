package hanium.server.i_luv_book.domain.auth.application;

import hanium.server.i_luv_book.domain.auth.dto.response.*;
import hanium.server.i_luv_book.domain.user.domain.Parent;
import hanium.server.i_luv_book.domain.user.domain.Role;
import hanium.server.i_luv_book.domain.user.infra.UserDataJpaRepository;
import hanium.server.i_luv_book.domain.auth.config.LoginWebConfig;
import hanium.server.i_luv_book.domain.auth.domain.LoginType;
import hanium.server.i_luv_book.domain.auth.util.LoginWebClientUtil;
import hanium.server.i_luv_book.global.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginService {

    private final JwtUtil jwtUtil;
    private final UserDataJpaRepository userDataJpaRepository;
    private final LoginWebClientUtil webClientUtil;
    private final LoginWebConfig loginWebConfig;
    public LoginFormResDTO getLoginFormUrl(LoginType type) {
        return new LoginFormResDTO(type.getLoginFormUrl());
    }
    public JwtTokenResponse generateJWTForKaKao(String code, LoginType type) {
        MultiValueMap<String, String> map = getMultiValueMap(code,LoginType.KAKAO);
        UserInfoDTO userInfo = getUserInfo(map,type);
        Optional<Parent> optionalParent = userDataJpaRepository.findBySocialIdAndLoginType(userInfo.getSocialId(), type);

        if(optionalParent.isPresent()) {
            Parent parent = optionalParent.get();
            return generateJwtToken(parent);
        } else {
            //refator 필요
            Parent parent = new Parent(userInfo.getEmail(), Parent.MembershipType.FREE,Role.ROLE_FREE,type, userInfo.getSocialId());
            Parent savedParent = userDataJpaRepository.save(parent);
            return generateJwtToken(savedParent);
        }
    }

    private MultiValueMap<String, String> getMultiValueMap(String code,LoginType type) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        //구글과 카카오의 공통 body
        map.add("grant_type", "authorization_code");
        map.add("code", code);

        switch (type) {
            case GOOGLE:
                break;
            case KAKAO:
                map.add("client_id", loginWebConfig.getKakaoRestKey());
                map.add("redirect_uri", loginWebConfig.getKakaoRedirectUrl());
                map.add("client_secret", loginWebConfig.getKakaoClentSecret());
        }
        return map;
    }

    public void google(String code) {
        log.info("구글 서비스");
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", loginWebConfig.getGoogleClientId());
        map.add("client_secret", loginWebConfig.getGoogleClientSecret());
        map.add("code", code);
        map.add("grant_type", "authorization_code");
        map.add("redirect_uri", loginWebConfig.getGoogleRedirectUrl());
        webClientUtil.setMonoForGoogle(map);
    }
    private JwtTokenResponse generateJwtToken(Parent parent) {
        String accessToken = jwtUtil.generateAccessToken(parent.getId(), parent.getRole());
        String refreshToken = jwtUtil.generateRefreshToken(parent.getId());
        return new JwtTokenResponse(accessToken,refreshToken);
    }

    private UserInfoDTO getUserInfo(MultiValueMap<String, String> map, LoginType type) {

        switch (type) {
            case GOOGLE:
                return new UserInfoDTO("test","test");
            //KAKAO
            default:
                KakaoAccessTokenDTO kakaoAccessToken = webClientUtil.setMonoForKakao(map).block();
                KakaoUserInfoDTO kakaoUserInfo = webClientUtil.getUserInfoFromKakao(kakaoAccessToken.getAccess_token()).block();
                return new UserInfoDTO(kakaoUserInfo.getSub(), kakaoUserInfo.getEmail());

        }

    }
}

